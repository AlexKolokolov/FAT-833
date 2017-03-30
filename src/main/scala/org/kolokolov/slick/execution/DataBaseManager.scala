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
      userGroupTable.schema.create,
      groupTable ++= Seq(Group("User"), Group("Admin")),
      userTable ++= Seq(User("Bob Marley"), User("Ron Perlman"), User("Tom Waits")),
      userGroupTable ++= Seq(UserGroup(1,1), UserGroup(2,1), UserGroup(3,1), UserGroup(3,2))
    )
    db.run(setup)
  }

  def cleanDB: Future[Unit] = {
    val dropTables = DBIO.seq(
      userGroupTable.schema.drop,
      groupTable.schema.drop,
      userTable.schema.drop
    )
    db.run(dropTables)
  }
}