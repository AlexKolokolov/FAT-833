package org.kolokolov.typeSystem

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
object CaseUserTest extends App{

  case class User (firstName: String, lastName: String)

  val u = User("Bob", "Marley")
  println(u.fullName)

  implicit class BetterUser(user: User) {
    def fullName: String = user.firstName + " " + user.lastName
  }
}




