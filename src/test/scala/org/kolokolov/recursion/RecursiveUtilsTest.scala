package org.kolokolov.recursion

import org.scalatest.FunSuite

/**
  * Created by andersen on 23.03.2017.
  */
class RecursiveUtilsTest extends FunSuite {

  test ("findMax should return 13") {
    val testList = List(1, 5, 10, -1, 2, 13, 9, 11)
    val expectedResult = 13
    assert(RecursiveUtils.findMax(testList) == expectedResult)
  }

  test ("findMinMax should return (13, -1)") {
    val testList = List(1, 5, 10, -1, 2, 13, 9, 11)
    val expectedResult = (-1, 13)
    assert(RecursiveUtils.findMinMax(testList) == expectedResult)
  }

  test ("splitByNegative should return (List(-1, -4),List(0, 5, 3))") {
    val testList = List(0, 5, -1, 3, -4)
    val expectedResult = (List(-1, -4),List(0, 5, 3))
    assert(RecursiveUtils.splitByNegative(testList) == expectedResult)
  }

  test ("splitByPredicate should return (List(-1, -4),List(0, 5, 3))") {
    val testList = List(0, 5, -1, 3, -4)
    val expectedResult = (List(-1, -4),List(0, 5, 3))
    val isNegative = (n: Int) => n < 0
    assert(RecursiveUtils.splitByPredicate(testList, isNegative) == expectedResult)
  }

  test ("splitByPredicate should return (List(0, -4),List(5, -1, 3))") {
    val testList = List(0, 5, -1, 3, -4)
    val expectedResult = (List(0, -4),List(5, -1, 3))
    val isEven = (n: Int) => n % 2 == 0
    assert(RecursiveUtils.splitByPredicate(testList, isEven) == expectedResult)
  }
}
