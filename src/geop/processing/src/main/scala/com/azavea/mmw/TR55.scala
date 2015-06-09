package com.azavea.mmw

import geotrellis.vector._
import geotrellis.vector.io.json._
import geotrellis.raster._
import geotrellis.raster.histogram._
import geotrellis.raster.rasterize.{Rasterizer, Callback}
import geotrellis.spark._

import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import scala.collection.mutable
import spire.syntax.cfor._

object TR55 {
  def countCombinations(geom: Geometry, rasterExtent: RasterExtent, nlcdTile: Tile, soilTile: Tile): Map[(Int, String), Int] = {
    val result = mutable.Map[(Int, String), Int]()
    Rasterizer.foreachCellByGeometry(geom, rasterExtent)(
      new Callback {
        def apply(col: Int, row: Int): Unit = {
          Constants.wordToSoil.get(soilTile.get(col, row)) match {
            case Some(soilType) =>
              val c = (nlcdTile.get(col, row), soilType)
              result(c) = result.getOrElseUpdate(c, 0) + 1
            case None =>
              // Unknown soil type. TODO: How do we handle this case?
          }
        }
      }
    )
    result.toMap
  }

  /** Returns a map of values to counts */
  def combinations(nlcd: RasterRDD[SpatialKey], soil: RasterRDD[SpatialKey], polygon: Polygon): Map[(Int, String), Int] = {
    val mapTransform = nlcd.metaData.mapTransform
    val nlcdAndSoil = nlcd.join(soil)
    val combinationCounts: RDD[Map[(Int, String), Int]] = 
      nlcdAndSoil
        .map { case (key, (nlcdTile, soilTile)) =>
          val extent = mapTransform(key) // transform spatial key to map extent
          val rasterExtent = RasterExtent(extent, nlcdTile.cols, nlcdTile.rows) // transform extent to raster extent
          val clippedPolygon = polygon & extent

          (polygon & extent).toGeometry match {
            case Some(geom) => countCombinations(geom, rasterExtent, nlcdTile, soilTile)
            case None => Map()
          }
        }

    combinationCounts
      .reduce { (m1, m2) =>
        (m1.toSeq ++ m2.toSeq)
          .groupBy(_._1)
          .map { case (key, counts) => (key, counts.map(_._2).sum) }
          .toMap
       }
  }
}
