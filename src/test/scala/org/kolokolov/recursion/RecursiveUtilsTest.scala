package org.kolokolov.recursion

import org.scalatest.FunSuite

/**
  * Created by andersen on 23.03.2017.
  */
class RecursiveUtilsTest extends FunSuite {

  test {"findMax should return 13"} {
    val testList = List(1, 5, 10, -1, 2, 13, 9, 11)
    val expectedResult = 13
    assert(RecursiveUtils.findMax(testList) == expectedResult)
  }

  test {"findMax should return (13, -1)"} {
    val testList = List(1, 5, 10, -1, 2, 13, 9, 11)
    val expectedResult = (-1, 13)
    assert(RecursiveUtils.findMinMax(testList) == expectedResult)
  }
}
