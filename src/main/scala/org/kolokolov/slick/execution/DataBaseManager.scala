package org.kolokolov.slick.execution

import org.kolokolov.slick.crud.{GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class DataBaseManager(override val profile: JdbcProfile) extends UserGroupCRUDModule with UserCRUDModule with GroupCRUDModule {

  import profile.api._

  def setupDB: Future[Unit] = {
    val setup = DBIO.seq(
      groupTable.schema.create,
      userTable.schema.create,
      userGroupTable.schema.create
    ).transactionally
    dataBase.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      userGroupTable.schema.drop,
      groupTable.schema.drop,
      userTable.schema.drop
    ).transactionally
    try {
      dataBase.run(dropTables)
    } finally dataBase.close
  }
}