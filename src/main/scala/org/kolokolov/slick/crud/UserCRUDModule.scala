package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User, UserGroup}

import scala.concurrent.Future

/**
  * Created by andersen on 04.04.2017.
  */
trait UserCRUDModule extends AbstractCRUDModule {

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user_table") with TableHasId[User] {
    def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("user_name")
    def * = (name, id) <> (User.tupled, User.unapply)
  }

  protected lazy val userTable = TableQuery[UserTable]

  class UserCRUD extends AbstractCRUD[User, UserTable] {
    override protected lazy val dataTable: TableQuery[UserTable] = userTable
  }
}
