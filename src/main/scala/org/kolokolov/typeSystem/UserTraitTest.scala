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

  class BetterUser(val firstName: String, val lastName: String) extends User {
    def fullName = s"$firstName $lastName"
  }

  implicit def userToBetterUser(u: User): BetterUser = new BetterUser(u.firstName, u.lastName)
}
