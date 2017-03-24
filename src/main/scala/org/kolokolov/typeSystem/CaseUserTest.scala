package org.kolokolov.typeSystem

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */

object CaseUserTest extends App{
  case class User (firstName: String, lastName: String)

  val u = User("Bob", "Marley")
  println(u.fullName)

  class BetterUser(override val firstName: String, override val lastName: String) extends User(firstName, lastName) {
    def fullName = s"$firstName $lastName"
  }

  implicit def userToBetterUser(u: User): BetterUser = new BetterUser(u.firstName, u.lastName)
}




