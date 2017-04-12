package org.kolokolov.slick

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.crud.{GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import org.kolokolov.slick.model.{Group, User, UserGroup}

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class TestDataBaseManager
  extends UserGroupCRUDModule
  with UserCRUDModule
  with GroupCRUDModule {

  this: DatabaseProfile =>

  import profile.api._

  def setupDB: Future[Unit] = {
    val setup = DBIO.seq(
      UserCRUD.dataTable.schema.create,
      GroupCRUD.dataTable.schema.create,
      UserGroupCRUD.dataTable.schema.create,
      GroupCRUD.dataTable ++= Seq(Group("User"), Group("Admin")),
      UserCRUD.dataTable ++= Seq(User("Bob Marley"), User("Ron Perlman"), User("Tom Waits")),
      UserGroupCRUD.dataTable ++= Seq(UserGroup(1,1), UserGroup(2,1), UserGroup(3,1), UserGroup(3,2))
    ).transactionally
    dataBase.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      UserGroupCRUD.dataTable.schema.drop,
      GroupCRUD.dataTable.schema.drop,
      UserCRUD.dataTable.schema.drop
    ).transactionally
    dataBase.run(dropTables)
  }
}