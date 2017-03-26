package org.kolokolov

import org.scalatest.FlatSpec
import org.slf4j.LoggerFactory

/**
  * Created by Alexey Kolokolov on 20.03.2017.
  */
class UtilsTest extends FlatSpec {

  private val logger = LoggerFactory.getLogger("UtilsTest logger")

  logger.info("UtilsTest is running")

  "Utils.sum" should "return Some(6)" in {
    assert(Utils.sum(Some(1), Some(2), Some(3)) == Some(6))
  }

  it should "return None" in {
    assert(Utils.sum(Some(1), Some(2), None).isEmpty)
  }
}
