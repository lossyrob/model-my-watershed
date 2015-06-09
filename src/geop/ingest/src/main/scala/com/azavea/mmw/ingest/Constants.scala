package com.azavea.mmw.ingest

import com.azavea.mmw._

object Constants {
  def wordToSoilType(w: Int): Option[SoilType] =
    wordSoilMap.get(w).map(resolveSoilType(_))

  private def resolveSoilType(s: String): SoilType =
    s match {
      case "A" => SoilTypeA
      case "B" => SoilTypeB
      case "C" => SoilTypeC
      case "D" => SoilTypeD
      case "A/D" => SoilTypeA
      case "B/D" => SoilTypeB
      case "C/D" => SoilTypeC
  }

  /** maps a 32-bit word to a soil type */
  private val wordSoilMap =
    Map(
      1603364 -> "A",
      1603365 -> "A/D",
      1603366 -> "A",
      1603367 -> "B/D",
      1603369 -> "A/D",
      1603371 -> "B/D",
      1603372 -> "A",
      1603373 -> "C/D",
      1603374 -> "A",
      1603377 -> "B/D",
      1603378 -> "A",
      1603379 -> "A",
      1603380 -> "A",
      1603381 -> "A",
      1603382 -> "A",
      1603383 -> "A",
      1603384 -> "A",
      1603391 -> "A",
      1603393 -> "A",
      1603395 -> "B/D",
      1603396 -> "B/D",
      1603397 -> "A",
      1603398 -> "A",
      1603399 -> "A",
      1603400 -> "A",
      1603403 -> "B/D",
      1603404 -> "A",
      1603406 -> "A/D",
      1603407 -> "B",
      1603408 -> "B",
      1603409 -> "B",
      1603410 -> "B",
      1603411 -> "B",
      1603412 -> "B",
      1603413 -> "B",
      1603414 -> "B",
      1603415 -> "B",
      1603416 -> "A",
      1603417 -> "A",
      1603418 -> "A",
      1603419 -> "A",
      1603420 -> "A",
      1603421 -> "A/D",
      1603422 -> "A/D",
      1603423 -> "A",
      1603424 -> "A",
      1603425 -> "A",
      1603426 -> "A",
      1603427 -> "A",
      1603428 -> "A",
      1603433 -> "D",
      1603436 -> "A/D",
      1603438 -> "D",
      1603439 -> "D",
      1603440 -> "A/D",
      1603441 -> "D",
      1603442 -> "D",
      1603443 -> "C/D",
      1603444 -> "C/D",
      1603445 -> "B/D",
      1603447 -> "A/D",
      1603448 -> "B/D",
      1603449 -> "B/D",
      1603451 -> "A/D",
      1603454 -> "A/D",
      1603455 -> "C/D",
      1603457 -> "A/D",
      1603458 -> "A/D",
      1603460 -> "A",
      1603461 -> "A",
      1603462 -> "A",
      1603463 -> "A",
      1603464 -> "A",
      1603465 -> "A",
      1603467 -> "A/D",
      1603468 -> "C",
      1603470 -> "A",
      1603471 -> "A",
      1603472 -> "A",
      1603473 -> "A",
      1603474 -> "A",
      1603476 -> "A",
      1603480 -> "B",
      1603482 -> "B",
      1603484 -> "B",
      1603489 -> "A/D",
      1603490 -> "B",
      1603491 -> "C/D",
      1603495 -> "A/D",
      1603496 -> "C",
      1603497 -> "C",
      1603498 -> "C",
      1603501 -> "A",
      1603502 -> "A",
      1603504 -> "C",
      1603506 -> "C",
      1603570 -> "B/D",
      1606010 -> "A",
      1606012 -> "A/D",
      1606013 -> "B/D",
      1606014 -> "B/D",
      1606015 -> "C/D",
      1606016 -> "A",
      1606018 -> "B/D",
      1606020 -> "C/D",
      1606021 -> "A",
      1606022 -> "A",
      1606023 -> "A",
      1606024 -> "A",
      1606025 -> "A",
      1606026 -> "A",
      1606027 -> "A",
      1606029 -> "C/D",
      1606030 -> "A",
      1606031 -> "A",
      1606032 -> "B/D",
      1606034 -> "B/D",
      1606036 -> "B/D",
      1606037 -> "A",
      1606038 -> "A",
      1606039 -> "A",
      1606040 -> "A",
      1606041 -> "A",
      1606042 -> "A/D",
      1606044 -> "B",
      1606045 -> "B",
      1606046 -> "B",
      1606047 -> "B",
      1606048 -> "B",
      1606052 -> "B",
      1606053 -> "B",
      1606054 -> "B",
      1606056 -> "B",
      1606057 -> "B",
      1606058 -> "A",
      1606059 -> "A",
      1606060 -> "A",
      1606061 -> "A",
      1606067 -> "A",
      1606068 -> "A/D",
      1606069 -> "A/D",
      1606070 -> "A",
      1606071 -> "A",
      1606072 -> "A",
      1606073 -> "A",
      1606075 -> "A",
      1606076 -> "A",
      1606077 -> "A",
      1606079 -> "C/D",
      1606080 -> "D",
      1606081 -> "D",
      1606082 -> "A/D",
      1606083 -> "A/D",
      1606086 -> "C",
      1606088 -> "C",
      1606090 -> "C/D",
      1606093 -> "D",
      1606094 -> "D",
      1606095 -> "B/D",
      1606096 -> "A/D",
      1606097 -> "B/D",
      1606098 -> "B/D",
      1606100 -> "C",
      1606102 -> "C",
      1606105 -> "C",
      1606108 -> "C",
      1606111 -> "C",
      1606114 -> "A/D",
      1606117 -> "C/D",
      1606119 -> "C",
      1606124 -> "C/D",
      1606126 -> "A",
      1606127 -> "A",
      1606129 -> "A",
      1606138 -> "C",
      1606139 -> "C",
      1606141 -> "A/D",
      1606142 -> "A/D",
      1606151 -> "A",
      1606157 -> "A",
      1606172 -> "A",
      1606173 -> "A",
      1606174 -> "A",
      1606175 -> "A",
      1606176 -> "B",
      1606179 -> "B",
      1606180 -> "B",
      1606181 -> "B",
      1606182 -> "B",
      1606183 -> "B",
      1606184 -> "C/D",
      1606186 -> "C/D",
      1606187 -> "A/D",
      1606188 -> "A",
      1606190 -> "C",
      1606194 -> "C",
      1606195 -> "C",
      1606197 -> "C/D",
      1606199 -> "C",
      1606201 -> "C",
      1606202 -> "C",
      1606204 -> "C",
      1606205 -> "B/D",
      1608677 -> "C/D",
      1608682 -> "C",
      1608683 -> "B",
      1608685 -> "B",
      1608854 -> "B",
      1675902 -> "A",
      1675903 -> "A",
      1675904 -> "A",
      1675905 -> "A",
      1727157 -> "C",
      2479763 -> "B/D",
      2479764 -> "C",
      2479765 -> "C",
      2479766 -> "A/D",
      2479767 -> "C/D",
      2479768 -> "C/D",
      2479769 -> "B/D",
      2479770 -> "B/D",
      2479771 -> "B/D",
      2479772 -> "B",
      2479773 -> "A",
      2479774 -> "C",
      2479775 -> "C",
      2479777 -> "C",
      2479778 -> "A",
      2479779 -> "A",
      2479781 -> "A",
      2479782 -> "A",
      2479783 -> "A",
      2479784 -> "A",
      2479785 -> "A",
      2479786 -> "A",
      2479787 -> "B",
      2479788 -> "A/D",
      2479789 -> "B",
      2479790 -> "B",
      2479791 -> "B",
      2479792 -> "B",
      2479793 -> "B",
      2479794 -> "B",
      2479795 -> "B",
      2479796 -> "B",
      2479797 -> "B",
      2479798 -> "B",
      2479799 -> "B",
      2479800 -> "C",
      2479801 -> "C",
      2479802 -> "C",
      2479803 -> "C",
      2479804 -> "B/D",
      2479805 -> "B/D",
      2479806 -> "B/D",
      2479807 -> "B/D",
      2479808 -> "C/D",
      2479809 -> "B",
      2479810 -> "B",
      2479811 -> "C/D",
      2479812 -> "D",
      2479813 -> "D",
      2479814 -> "D",
      2479815 -> "D",
      2479816 -> "D",
      2479817 -> "C",
      2479818 -> "C",
      2479819 -> "B",
      2479820 -> "B",
      2479821 -> "B",
      2479822 -> "B",
      2479835 -> "C/D",
      2479836 -> "C/D",
      2479837 -> "C",
      2479838 -> "C",
      2479839 -> "C",
      2479840 -> "C",
      2479841 -> "C/D",
      2479842 -> "C",
      2479843 -> "C",
      2479844 -> "C",
      2479845 -> "C",
      2479846 -> "C/D",
      2479847 -> "C",
      2479848 -> "C",
      2479849 -> "C",
      2479850 -> "C",
      2479851 -> "C",
      2479852 -> "C",
      2479853 -> "B/D",
      2479854 -> "B/D",
      2479855 -> "C",
      2479856 -> "B",
      2479857 -> "B",
      2479858 -> "B",
      2479859 -> "C",
      2479860 -> "C",
      2479861 -> "C",
      2479862 -> "B",
      2479865 -> "B/D",
      2479866 -> "D",
      2479867 -> "D",
      2479868 -> "D",
      2479869 -> "B",
      2479870 -> "B",
      2479871 -> "C/D",
      2479872 -> "C/D",
      2479873 -> "B",
      2479874 -> "B",
      2479875 -> "B",
      2479876 -> "B",
      2479877 -> "B",
      2479878 -> "C/D",
      2479879 -> "B",
      2479880 -> "B",
      2479881 -> "B",
      2479882 -> "C",
      2479883 -> "C",
      2479884 -> "C",
      2479885 -> "B",
      2479886 -> "B",
      2479887 -> "B",
      2479888 -> "B",
      2479889 -> "B",
      2479890 -> "B",
      2479891 -> "B",
      2479892 -> "B",
      2479893 -> "B",
      2479894 -> "B",
      2479895 -> "B",
      2479896 -> "B",
      2479897 -> "C/D",
      2479898 -> "C/D",
      2479899 -> "C/D",
      2479900 -> "B",
      2479901 -> "B"
    )
}