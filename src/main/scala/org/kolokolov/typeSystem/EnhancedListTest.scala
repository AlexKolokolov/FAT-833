package org.kolokolov.typeSystem

import scala.util.Random

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
object EnhancedListTest extends App {

  val xs: List[Int] = List(1, 2, 3, 4, 5)
  println(xs.randomElement)

  implicit class BetterList[A](sourceList: List[A])  {
    def randomElement: A = sourceList(Random.nextInt(sourceList.length))
  }
}
