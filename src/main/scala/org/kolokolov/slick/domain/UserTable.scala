package org.kolokolov.slick.domain

import slick.jdbc.PostgresProfile.api._

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
class UserTable(tag: Tag) extends Table[User](tag, "user_table") {
  def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("user_name")
  def * = (name, id) <> (User.tupled, User.unapply)
}

case class User(name: String, id: Int = 0)


