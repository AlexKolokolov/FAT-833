package org.kolokolov.slick.crud

import org.kolokolov.slick.model.User

/**
  * Created by Alexey Kolokolov on 04.04.2017.
  */
trait UserCRUDModule extends AbstractCRUDModule {

  self: DatabaseProfile =>

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user_table") with TableHasId[User] {
    def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("user_name")
    def * = (name, id) <> (User.tupled, User.unapply)
  }

  object UserCRUD extends AbstractCRUD[User, UserTable] {
    override lazy val dataTable: TableQuery[UserTable] = TableQuery[UserTable]
  }
}
