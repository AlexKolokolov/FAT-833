package org.kolokolov.slick.execution

import org.kolokolov.slick.crud.{DatabaseProfile, GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class DataBaseManager(override val profile: JdbcProfile) extends UserGroupCRUDModule with UserCRUDModule with GroupCRUDModule with DatabaseProfile {

  import profile.api._

  def setupDB: Future[Unit] = {
    val setup = DBIO.seq(
      UserCRUD.dataTable.schema.create,
      GroupCRUD.dataTable.schema.create,
      UserGroupCRUD.dataTable.schema.create
    ).transactionally
    dataBase.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      UserGroupCRUD.dataTable.schema.drop,
      GroupCRUD.dataTable.schema.drop,
      UserCRUD.dataTable.schema.drop
    ).transactionally
    try {
      dataBase.run(dropTables)
    } finally dataBase.close
  }
}