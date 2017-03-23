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

  def findMinMax(xs: List[Int]): (Int,Int) ={
    @tailrec def findMinMaxRec(xs: List[Int], min: Int, max: Int): (Int,Int) = {
      def lessAndGreater(a: Int, b: Int): (Int,Int) = {
        if (a < b) (a,b) else (b,a)
      }
      if (xs == Nil) (min,max)
      else findMinMaxRec(xs.tail, lessAndGreater(xs.head, min)._1, lessAndGreater(xs.head, max)._2)
    }
    findMinMaxRec(xs, xs.head, xs.head)
  }
}
