package org.kolokolov

import org.scalatest.FunSuite

/**
  * Created by andersen on 21.03.2017.
  */
class UtilsFunTest extends FunSuite {

  test("Utils.sum returns Some(6)") {
    assert(Utils.sum(Some(1), Some(2), Some(3)).contains(6))
  }

  test("Utils.sum returns None") {
    assert(Utils.sum(Some(1), Some(2), None).isEmpty)
  }
}
