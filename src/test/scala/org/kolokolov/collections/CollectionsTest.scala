package org.kolokolov.collections

import org.scalatest.FunSuite

/**
  * Created by andersen on 21.03.2017.
  */
class CollectionsTest extends FunSuite {

  private val xs = List("zero", "one", "two", "three", "four", "five", "six", "seven")
  private val testList = List(List(1,2,3), List(4), Nil, List(5, 6, 7))
  private val testString = "aaabbbccdefffab"

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

  test("sortAndSplitByLength should return (List(\"one\", \"two\", \"six\"),List(\"zero\", \"four\", \"five\", \"three\", \"seven\"))") {
    val expectedResult = (List("one", "two", "six"),
                          List("zero", "four", "five", "three", "seven"))
    assert(Collections.sortAndSplitByLength(xs,4) == expectedResult)
  }

  test("flattenSeq should return List(1,2,3,4,5,6,7)") {
    val expectedResult = List(1,2,3,4,5,6,7)
    assert(Collections.flattenSeq(testList) == expectedResult)
  }

  test("countLetters should return List('a'-> 4, 'b' -> 4, 'c' -> 2, 'd' -> 1, 'e' -> 1, 'f' -> 3)") {
    val expectedResult = List('a'-> 4, 'b' -> 4, 'c' -> 2, 'd' -> 1, 'e' -> 1, 'f' -> 3)
    assert(Collections.countLetters(testString) == expectedResult)
  }
}
