package org.kolokolov

import org.scalatest.FlatSpec

/**
  * Created by Alexey Kolokolov on 20.03.2017.
  */
class UtilsTest extends FlatSpec {

  "Utils.sum" should "return Some(6)" in {
    assert(Utils.sum(Some(1), Some(2), Some(3)).contains(6))
  }

  it should "return None" in {
    assert(Utils.sum(Some(1), Some(2), None).isEmpty)
  }
}
