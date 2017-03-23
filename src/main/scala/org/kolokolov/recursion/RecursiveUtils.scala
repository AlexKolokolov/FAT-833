package org.kolokolov.recursion

import scala.annotation.tailrec

/**
  * Created by andersen on 23.03.2017.
  */
object RecursiveUtils {

  def findMax(xs: List[Int]): Int = {
    @tailrec def findMaxRec(xs: List[Int], max: Int): Int = {
      if (xs == Nil) max
      else if (xs.head > max) findMaxRec(xs.tail, xs.head)
      else findMaxRec(xs.tail, max)
    }
    findMaxRec(xs,xs.head)
  }
}
