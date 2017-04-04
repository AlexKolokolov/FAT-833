package org.kolokolov.slick

import org.kolokolov.slick.crud.{GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import org.kolokolov.slick.model.{Group, User, UserGroup}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class TestDataBaseManager(override val profile: JdbcProfile) extends UserGroupCRUDModule with UserCRUDModule with GroupCRUDModule {

  import profile.api._

  def setupDB: Future[Unit] = {
    val setup = DBIO.seq(
      groupTable.schema.create,
      userTable.schema.create,
      userGroupTable.schema.create,
      groupTable ++= Seq(Group("User"), Group("Admin")),
      userTable ++= Seq(User("Bob Marley"), User("Ron Perlman"), User("Tom Waits")),
      userGroupTable ++= Seq(UserGroup(1,1), UserGroup(2,1), UserGroup(3,1), UserGroup(3,2))
    ).transactionally
    dataBase.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      userGroupTable.schema.drop,
      groupTable.schema.drop,
      userTable.schema.drop
    ).transactionally
    dataBase.run(dropTables)
  }
}