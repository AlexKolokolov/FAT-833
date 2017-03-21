package org.kolokolov.collections

import org.scalatest.FunSuite

/**
  * Created by andersen on 21.03.2017.
  */
class CollectionsTest extends FunSuite {

  val xs = List("zero", "one", "two", "three", "four", "five", "six", "seven")

  test("zipToIndexes should return List((0, \"zero\"), (1, \"one\") ...)") {
    val expectedResult = List((0,"zero"), (1,"one"), (2,"two"), (3,"three"), (4,"four"), (5,"five"), (6,"six"), (7,"seven"))
    assert(Collections.zipToIndexes(xs) == expectedResult)
  }
}
