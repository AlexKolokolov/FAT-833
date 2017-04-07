package org.kolokolov.html

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
object HtmlGenerator extends App {

  def div(id: String, css: String = "")(content: => String): String = {
    css match {
      case "" => s"<div id='$id'>$content</div>"
      case _ => s"<div id='$id' class='$css'>$content</div>"
    }
  }

  val html1 = div(id = "left-panel", css = "red") {
    val name = DB.getUserName
    s"Hello, $name"
  }

  println(html1)

  val html2 = div(id = "outer") {
    div(id = "inner") {
      "Hello"
    }
  }

  println(html2)
}

object DB {
  def getUserName: String = {
    "John"
  }
}

