package org.kolokolov

import org.scalatest.FlatSpec

/**
  * Created by andersen on 20.03.2017.
  */
class UtilsTest extends FlatSpec{

  "Utils.sum" should "return Some(6)" in {
    assert(Utils.sum(Some(1), Some(2), Some(3)) == Some(6))
  }
}
