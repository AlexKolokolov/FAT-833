package org.kolokolov.slick.domain

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
trait UserGroupModule extends UserModule with GroupModule {

  import profile.api._

  class UserGroupTable(tag: Tag) extends Table[UserGroup](tag, "user_group_table") {
    def userId = column[Int]("user_id")
    def groupId = column[Int]("group_id")
    def * = (userId, groupId) <> (UserGroup.tupled, UserGroup.unapply)
    def pk = primaryKey("primary_key", (userId, groupId))
    def user = foreignKey("user_fk", userId, userTable)(_.id,
      onDelete = ForeignKeyAction.Cascade)
    def group = foreignKey("group_fk", groupId, groupTable)(_.id)
  }

  case class UserGroup(userId: Int, groupId: Int)

  lazy val userGroupTable = TableQuery[UserGroupTable]
}



