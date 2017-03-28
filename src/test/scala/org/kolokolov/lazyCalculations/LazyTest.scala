package org.kolokolov.lazyCalculations

import org.scalatest.FunSuite

/**
  * Created by andersen on 27.03.2017.
  */
class LazyTest extends FunSuite {

  test("factorials(4).take(5).toList should return List(24, 120, 720, 5040, 40320)") {
    val expectedResult = List(24, 120, 720, 5040, 40320)
    assert(LazyDemo.factorials(4).take(5).toList == expectedResult)
  }
}
