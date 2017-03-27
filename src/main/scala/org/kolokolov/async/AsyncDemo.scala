package org.kolokolov.async

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
object AsyncDemo extends App {
  def countA: Int = {
    Thread.sleep(2000)
    1
  }

  def countB: Int = {
    Thread.sleep(2000)
    10
  }

  def countC: Int = {
    Thread.sleep(2000)
    100
  }

  def sumAsync(a: => Int, b: => Int, c: => Int)(implicit ec: ExecutionContext): Future[Int] = {
    val futureA = Future(a)
    val futureB = Future(b)
    val futureC = Future(c)
    for {
      resA <- futureA
      resB <- futureB
      resC <- futureC
    } yield resA + resB + resC
  }

  def measureTime(block: => Unit): Unit = {
    val start = System.nanoTime
    block
    val stop = System.nanoTime
    println("Elapsed time: " + (stop - start).asInstanceOf[Double] / 1000000 + " ms" )
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  measureTime {
    sumAsync(countA, countB, countC).onComplete { result => println("Async result: " + result) }
  }

  measureTime {
    println("Sync result: " + (countA + countB + countC))
  }
}