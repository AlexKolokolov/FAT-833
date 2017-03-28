package org.kolokolov.arguments

import org.scalatest.FunSuite

/**
  * Created by Alexey Kolokolov on 27.03.2017.
  */
class ArgumentsPassingTest extends FunSuite {

  private[ArgumentsPassingTest] var counter: Int = 0

  def intFunction: Int = {
    counter += 1
    10 * counter
  }

  val refIntFunction: () => Int = () => {
    counter += 1
    10 * counter
  }

  test("printTwiceByValue should make counter == 1") {
    counter = 0
    ArgumentsPassingDemo.printTwiceByValue(intFunction)
    assert(counter == 1)
  }

  test("printTwiceByName should make counter == 2") {
    counter = 0
    ArgumentsPassingDemo.printTwiceByName(intFunction)
    assert(counter == 2)
  }

  test("printTwiceByReference should make counter == 2") {
    counter = 0
    ArgumentsPassingDemo.printTwiceByReference(refIntFunction)
    assert(counter == 2)
  }
}
