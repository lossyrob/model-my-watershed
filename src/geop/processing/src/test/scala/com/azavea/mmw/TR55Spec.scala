package com.azavea.mmw

import geotrellis.spark._
import geotrellis.raster._
import geotrellis.raster.histogram._

import geotrellis.vector._
import geotrellis.vector.io.json._

import scala.collection.mutable
import org.scalatest._
import spire.syntax.cfor._

object Utility {
  import spray.json._

  val extents = Map[String, Polygon](
    "ProperSubset" ->
      """{"type":"Feature","properties":{"name":6},"geometry":{
        "type":"Polygon",
        "coordinates":[[
          [-75.72533952081164, 39.76],
          [-75.72533952081164, 39.8024743318554],
          [-75.58521226772196, 39.77],
          [-75.58521226772196, 39.7342308109474],
          [-75.72533952081164, 39.76]]]}}""".parseJson.convertTo[Polygon],
    "NonIntersecting" ->
      """{"type":"Feature","properties":{"name":9},"geometry":{
          "type":"Polygon",
          "coordinates":[[
            [31.137728, 29.975284],
            [31.128097, 29.972484],
            [31.130860, 29.976007],
            [31.134046, 29.979194],
            [31.137728, 29.975284]]]}}""".parseJson.convertTo[Polygon]
  )

  def computeHistogram(nlcd: RasterRDD[SpatialKey], soil: RasterRDD[SpatialKey]) = {
    val n = nlcd.stitch
    val s = soil.stitch

    assert(n.cols == s.cols)
    assert(n.rows == s.rows)

    val m = mutable.Map[(Int, Option[String]), Int]()
    cfor(0)(_ < n.rows, _ + 1) { row =>
      cfor(0)(_ < n.cols, _ + 1) { col =>
        val soilType = TR55.wordToSoil.get(s.get(col,row))
        if (soilType != None) {
          val c = (n.get(col, row), soilType)
          if(!m.contains(c)) { m(c) = 0 }
          m(c) += 1
        }
      }
    }
    m
  }
}

class TR55Spec extends FunSpec with Matchers with OnlyIfCanRunSpark {
  import Utility._

  ifCanRunSpark {
    describe("TR55") {

      it("should compute the correct histogram for Delaware rasters, full extent") {
        val nlcd = TestFiles.delawareNLCD
        val soil = TestFiles.delawareSoil

        val polygon: Polygon = nlcd.metaData.extent.toPolygon
        val actual = TR55.combinations(nlcd, soil, polygon)
        val expected = Utility.computeHistogram(nlcd, soil)

        actual should be (expected)
      }

      it("should compute the correct histogram for a non-intersecting polygon") {
        val nlcd = TestFiles.delawareNLCD
        val soil = TestFiles.delawareSoil

        val polygon: Polygon = extents.get("NonIntersecting").get
        val actual = TR55.combinations(nlcd, soil, polygon)
        val expected = mutable.Map[(Int, Option[String]), Int]()

        actual should be (expected)
      }

      it("should produce a histogram for a proper subset (every value is leq that of the full extent)") {
        val nlcd = TestFiles.delawareNLCD
        val soil = TestFiles.delawareSoil

        val polygon: Polygon = extents.get("ProperSubset").get
        val smaller = TR55.combinations(nlcd, soil, polygon)
        val larger = Utility.computeHistogram(nlcd, soil)

        smaller foreach {
          case (key, small) =>
            larger.get(key) match {
              case Some(large) =>
                (small <= large) should be (0 <= 1)
              case None =>
                assert(false)
            }
        }
      }

    }
  }
}
