package org.kolokolov.slick.execution

import org.kolokolov.slick.domain.{Group, GroupTable, User, UserGroup, UserGroupTable, UserTable}
import slick.jdbc.PostgresProfile.api._

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
object SetupDB extends App{
  private val db = Database.forConfig("db.config")
  private lazy val userTable = TableQuery[UserTable]
  private lazy val groupTable = TableQuery[GroupTable]
  private lazy val userGroupTable = TableQuery[UserGroupTable]

  def setupDB: Unit = {
    val setup = DBIO.seq(
      groupTable.schema.create,
      userTable.schema.create,
      userGroupTable.schema.create,
      groupTable ++= Seq(Group("User"), Group("Admin")),
      userTable ++= Seq(User("Bob Marley"), User("Ron Perlman"), User("Tom Waits")),
      userGroupTable ++= Seq(UserGroup(1,1), UserGroup(2,1), UserGroup(3,1), UserGroup(3,2))
    )
    try {
      db.run(setup)
    } finally db.close
  }
  setupDB
}
