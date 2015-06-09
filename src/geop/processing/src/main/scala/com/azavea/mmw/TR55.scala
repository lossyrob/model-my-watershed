package com.azavea.mmw

import geotrellis.vector._
import geotrellis.vector.io.json._
import geotrellis.raster._
import geotrellis.raster.op.local._
import geotrellis.raster.histogram._
import geotrellis.raster.rasterize.{Rasterizer, Callback}
import geotrellis.spark._

import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import scala.collection.mutable
import spire.syntax.cfor._

object TR55 {
  private def emptyCombinationTile =
    IntArrayTile(Array.ofDim[Int](LandCover.COUNT * SoilType.COUNT), LandCover.COUNT, SoilType.COUNT)

  private def zeroCombinationTile =
    IntConstantTile(0, LandCover.COUNT, SoilType.COUNT)

  private def countCombinations(geom: Geometry, rasterExtent: RasterExtent, nlcdTile: Tile, soilTile: Tile): Tile = {
    val result = emptyCombinationTile
    Rasterizer.foreachCellByGeometry(geom, rasterExtent)(
      new Callback {
        def apply(col: Int, row: Int): Unit = {
          val classification = nlcdTile.get(col, row)
          val soilType = soilTile.get(col, row)

          // NOTE: We are not currently counting if there is NODATA in either value.
          if(isData(classification) && isData(soilType)) {
            val c = result.get(classification, soilType)
            result.set(classification, soilType, c + 1)
          }
        }
      }
    )
    result
  }

  /** Returns a map of values to counts */
  def combinations(nlcd: RasterRDD[SpatialKey], soil: RasterRDD[SpatialKey], polygon: Polygon): Map[(Int, Int), Int] = {
    val mapTransform = nlcd.metaData.mapTransform
    val nlcdAndSoil = nlcd.join(soil)
    val combinationCounts: RDD[Tile] = 
      nlcdAndSoil
        .map { case (key, (nlcdTile, soilTile)) =>
          val extent = mapTransform(key) // transform spatial key to map extent
          val rasterExtent = RasterExtent(extent, nlcdTile.cols, nlcdTile.rows) // transform extent to raster extent
          val clippedPolygon = polygon & extent

          (polygon & extent).toGeometry match {
            case Some(geom) => countCombinations(geom, rasterExtent, nlcdTile, soilTile)
            case None => zeroCombinationTile
          }
        }

    val countTile =
      combinationCounts
        .mapPartitions { part =>
          Seq(part.toSeq.localAdd).iterator
         }
        .reduce(_ + _)

    val result = mutable.Map[(Int, Int), Int]()
    countTile.foreach { (col, row, z) =>
      if(z != 0) {
        result((col, row)) = z
      }
    }

    result.toMap
  }
}
