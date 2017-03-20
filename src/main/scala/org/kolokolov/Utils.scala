package org.kolokolov

/**
  * Created by Alexey Kolokolov on 20.03.2017.
  */
object Utils {
  def sum(x: Option[Int], y: Option[Int], z: Option[Int]): Option[Int] = {
    for {
      _x <- x
      _y <- y
      _z <- z
    } yield _x + _y + _z
  }
}
