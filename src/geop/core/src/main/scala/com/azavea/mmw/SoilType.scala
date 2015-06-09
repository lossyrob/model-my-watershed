package com.azavea.mmw

abstract sealed trait SoilType { def code: Int }

object SoilType {
  val COUNT = 4

  implicit def soilToInt(s: SoilType): Int = s.code
  implicit def intToSoil(i: Int): SoilType =
    i match {
      case 0 => SoilTypeA
      case 1 => SoilTypeB
      case 2 => SoilTypeC
      case 3 => SoilTypeD
      case _ => sys.error("Invalid soil type code.")
    }
}

case object SoilTypeA extends SoilType { val code = 0 }
case object SoilTypeB extends SoilType { val code = 1 }
case object SoilTypeC extends SoilType { val code = 2 }
case object SoilTypeD extends SoilType { val code = 3 }
