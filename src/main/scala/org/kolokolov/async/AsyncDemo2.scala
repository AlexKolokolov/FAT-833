package org.kolokolov.async

import scala.concurrent.{ExecutionContext, Future}
import scala.async.Async._

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
object AsyncDemo2 extends App {
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
    async {
      await(Future(countA)) + await(Future(countB)) + await(Future(countC))
    }
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

  Thread.sleep(3000)
}

