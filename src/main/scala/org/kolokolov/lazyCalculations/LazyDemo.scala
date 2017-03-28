package org.kolokolov.lazyCalculations

import scala.annotation.tailrec

/**
  * Created by Alexey Kolokolov on 27.03.2017.
  */
object LazyDemo {

  def factorials(value: Long): Stream[Long] = {
    @tailrec def tailFact(x: Long = 1, fact: Long = 1): Long = {
      if (x >= value) fact * x
      else tailFact(x + 1, fact * x)
    }
    tailFact() #:: factorials(value + 1)
  }
}
