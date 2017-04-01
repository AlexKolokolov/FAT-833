package org.kolokolov.slick.execution

import org.kolokolov.slick.domain._
import slick.jdbc.JdbcProfile
import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class DataBaseManager(override val profile: JdbcProfile) extends UserGroupModule {

  import profile.api._

  private val db = Database.forConfig("db.config")

  def setupDB: Future[Unit] = {
    val setup = DBIO.seq(
      groupTable.schema.create,
      userTable.schema.create,
      userGroupTable.schema.create
    )
    db.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      userGroupTable.schema.drop,
      groupTable.schema.drop,
      userTable.schema.drop
    )
    try {
      db.run(dropTables)
    } finally db.close
  }
}