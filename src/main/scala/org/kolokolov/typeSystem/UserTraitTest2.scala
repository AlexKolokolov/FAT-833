package org.kolokolov.typeSystem

/**
  * Created by Alexey Kolokolov on 27.03.2017.
  */
object UserTraitTest2 extends App {

  trait User {
    def firstName: String
    def lastName: String
  }

  trait FullNameAware {
    this: User => def fullName: String = firstName + " " + lastName
  }

  val u = new User with FullNameAware {
    val firstName = "Bob"
    val lastName = "Marley"
  }
  println(u.fullName)


}
