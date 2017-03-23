package org.kolokolov.helpers

/**
  * Created by andersen on 23.03.2017.
  */
object MathHelpers {
  def lessAndGreater(a: Int, b: Int): (Int,Int) = {
    if (a < b) (a,b) else (b,a)
  }
}
