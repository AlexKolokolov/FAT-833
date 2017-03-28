package org.kolokolov.arguments

/**
  * Created by Alexey Kolokolov on 27.03.2017.
  */
object ArgumentsPassingDemo extends App {

  def printTwiceByValue(x: Int): Unit = {
    println("First time: x = " + x)
    println("Second time: x = " + x)
  }

  def printTwiceByName(x: => Int): Unit = {
    println("First time: x = " + x)
    println("Second time: x = " + x)
  }

  def printTwiceByReference(f: () => Int): Unit = {
    println("First time: x = " + f())
    println("Second time: x = " + f())
  }
}
