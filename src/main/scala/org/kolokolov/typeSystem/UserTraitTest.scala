package org.kolokolov.typeSystem

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
object UserTraitTest extends App {

  trait User {
    def firstName: String
    def lastName: String
  }

  val u: User = new User {
    val firstName = "Bob"
    val lastName = "Marley"
  }
  println(u.fullName)

  implicit class BetterUser(user: User) {
    def fullName: String = user.firstName + " " + user.lastName
  }
}
