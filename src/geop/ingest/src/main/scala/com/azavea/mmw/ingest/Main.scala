package com.azavea.mmw.ingest

import com.azavea.mmw._
import geotrellis.raster._
import geotrellis.raster.io.geotiff._

object Main {
  def main(args: Array[String]): Unit = {
    val inPath = args(0)
    val outPath = args(1)
    val gt = SingleBandGeoTiff(inPath)
    val converted =
      gt.mapTile { tile =>
        tile.map { z =>
          Constants.wordToSoilType(z) match {
            case Some(soilType) =>
              soilType.code
            case None =>
              NODATA
          }
        }
      }
    converted.write(outPath)
  }
}
