package org.kolokolov

import org.slf4j.LoggerFactory

/**
  * Created by Alexey Kolokolov on 20.03.2017.
  */
object Utils {

  private val logger = LoggerFactory.getLogger("Utils logger")

  def main(args: Array[String]): Unit = {
    println(sum(Some(1), Some(2), Some(3)))
  }

  def sum(x: Option[Int], y: Option[Int], z: Option[Int]): Option[Int] = {
    logger.debug("Method sum is running with x = {}, y = {} and z = {}", x, y, z)
    val result = for {
      _x <- x
      _y <- y
      _z <- z
    } yield _x + _y + _z
    logger.debug("Method sum returned {}", result)
    result
  }
}
