package org.kolokolov.typeSystem

/**
  * Created by andersen on 24.03.2017.
  */

object CaseUserTest extends App{
  case class User (firstName: String, lastName: String)

  class BetterUser(override val firstName: String, override val lastName: String) extends User(firstName, lastName) {
    def fullName = s"$firstName $lastName"
  }

  implicit def userToBetterUser(u: User): BetterUser = new BetterUser(u.firstName, u.lastName)

  val u = User("Bob", "Marley")
  println(u.fullName)
}




