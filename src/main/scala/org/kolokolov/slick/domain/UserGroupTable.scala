package org.kolokolov.slick.domain

import slick.jdbc.PostgresProfile.api._

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
class UserGroupTable(tag: Tag) extends Table[UserGroup](tag, "user_group_table") {
  def userId = column[Int]("user_id")
  def groupId = column[Int]("group_id")

  def * = (userId, groupId) <> (UserGroup.tupled, UserGroup.unapply)

  def user = foreignKey("user_fk", userId, TableQuery[UserTable])(_.id,
    onDelete = ForeignKeyAction.Cascade)
  def group = foreignKey("group_fk", groupId, TableQuery[GroupTable])(_.id)
}

case class UserGroup(userId: Int, groupId: Int)


