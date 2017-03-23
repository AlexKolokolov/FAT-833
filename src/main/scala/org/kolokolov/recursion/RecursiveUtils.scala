package org.kolokolov.recursion

import org.kolokolov.helpers.MathHelpers._
import scala.annotation.tailrec

/**
  * Created by Alexey Kolokolov on 23.03.2017.
  */
object RecursiveUtils {

  def findMax(xs: List[Int]): Int = {
    @tailrec def findMaxRec(xs: List[Int], max: Int): Int = {
      xs match {
        case Nil => max
        case h :: _ if h > max => findMaxRec(xs.tail, h)
        case _ => findMaxRec(xs.tail, max)
      }
    }
    findMaxRec(xs,xs.head)
  }

  def findMinMax(xs: List[Int]): (Int,Int) ={
    @tailrec def findMinMaxRec(xs: List[Int], min: Int, max: Int): (Int,Int) = {
      if (xs.isEmpty) (min,max)
      else findMinMaxRec(xs.tail, lessAndGreater(xs.head, min)._1, lessAndGreater(xs.head, max)._2)
    }
    findMinMaxRec(xs, xs.head, xs.head)
  }

  def splitByNegative(xs: List[Int]): (List[Int],List[Int]) = {
    @tailrec def splitRec(xs: List[Int], neg: List[Int], pos: List[Int]): (List[Int],List[Int]) = {
      xs match {
        case Nil => (neg, pos)
        case h :: _ if h >= 0 => splitRec(xs.tail, neg, h :: pos)
        case h :: _ => splitRec(xs.tail, h :: neg, pos)
      }
    }
    val result = splitRec(xs, Nil, Nil)
    (result._1.reverse, result._2.reverse)
  }

  def splitByPredicate(xs: List[Int], p: Int => Boolean): (List[Int],List[Int]) = {
    @tailrec def splitRec(xs: List[Int], first: List[Int], second: List[Int]): (List[Int],List[Int]) = {
      xs match {
        case Nil => (first,second)
        case h :: _ if p(h) => splitRec(xs.tail, h :: first, second)
        case h :: _ => splitRec(xs.tail, first, h :: second)
      }
    }
    val result = splitRec(xs, Nil, Nil)
    (result._1.reverse, result._2.reverse)
  }
}
