package org.kolokolov.helpers

import org.scalatest.FunSuite

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
class MathHelpersTest extends FunSuite {

  test ("lessAndGreater should return (5,10") {
    assert(MathHelpers.lessAndGreater(10,5) == (5,10))
  }

  test ("lessAndGreater should return (1,2") {
    assert(MathHelpers.lessAndGreater(1,2) == (1,2))
  }
}
