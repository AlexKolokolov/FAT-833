package org.kolokolov.typeSystem

/**
  * Created by Alexey Kolokolov on 24.03.2017.
  */
trait User {
  def firstName: String
  def lastName: String
}

class BetterUser(val firstName: String, val lastName: String) extends User {
  def fullName = s"$firstName $lastName"
}

object UserTest extends App {

  implicit def userToBetterUser(u: User): BetterUser = new BetterUser(u.firstName, u.lastName)

  val u: User = new BetterUser("Bob", "Marley")
  println(u.fullName)
}
