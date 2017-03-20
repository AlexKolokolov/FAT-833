package org.kolokolov

/**
  * Created by Alexey Kolokolov on 20.03.2017.
  */
object Utils {

  def main(args: Array[String]): Unit = {
    println(sum(Some(1), Some(2), Some(3)))
  }

  def sum(x: Option[Int], y: Option[Int], z: Option[Int]): Option[Int] = {
    for {
      _x <- x
      _y <- y
      _z <- z
    } yield _x + _y + _z
  }
}
