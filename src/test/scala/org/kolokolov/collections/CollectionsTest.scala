package org.kolokolov.collections

import org.scalatest.FunSuite

/**
  * Created by andersen on 21.03.2017.
  */
class CollectionsTest extends FunSuite {

  val xs = List("zero", "one", "two", "three", "four", "five", "six", "seven")

  test("zipToIndexes should return List((0, \"zero\"), (1, \"one\") ...)") {
    val expectedResult = List((0,"zero"),
      (1,"one"),
      (2,"two"),
      (3,"three"),
      (4,"four"),
      (5,"five"),
      (6,"six"),
      (7,"seven"))
    assert(Collections.zipToIndexes(xs) == expectedResult)
  }

  test("mapByLength should return Map(3 - > List(\"one\",\"two\",\"six\", 4 -> List(\"zero\",\"four\",\"five\"),...)") {
    val expectedResult = Map(
      3 -> List("one", "two", "six"),
      4 -> List("zero", "four", "five"),
      5 -> List("three", "seven"))
    assert(Collections.mapByLength(xs) == expectedResult)
  }

}
