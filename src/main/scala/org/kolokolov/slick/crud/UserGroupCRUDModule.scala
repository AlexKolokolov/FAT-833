package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User, UserGroup}

import scala.concurrent.Future

/**
  * Created by andersen on 04.04.2017.
  */
trait UserGroupCRUDModule extends DatabaseProfile {

  self: UserCRUDModule with GroupCRUDModule =>

  import profile.api._

  class UserGroupTable(tag: Tag) extends Table[UserGroup](tag, "user_group_table") {
    def userId = column[Int]("user_id")
    def groupId = column[Int]("group_id")
    def * = (userId, groupId) <> (UserGroup.tupled, UserGroup.unapply)
    def pk = primaryKey("primary_key", (userId, groupId))
    def user = foreignKey("user_fk", userId, userTable)(_.id,
      onDelete = ForeignKeyAction.Cascade)
    def group = foreignKey("group_fk", groupId, groupTable)(_.id,
      onDelete = ForeignKeyAction.Cascade)
  }

  protected lazy val userGroupTable = TableQuery[UserGroupTable]

  def addUserToGroup(userId: Int, groupId: Int): Future[Int] = {
    val addUserToGroupAction = userGroupTable += UserGroup(userId,groupId)
    dataBase.run(addUserToGroupAction)
  }

  def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = {
    val deleteUserFromGroupAction = userGroupTable.filter(_.userId === userId)
      .filter(_.groupId === groupId).delete
    dataBase.run(deleteUserFromGroupAction)
  }

  def getUsersByGroupId(groupId: Int): Future[Seq[(User, Group)]] = {
    val getUsersByGroupIdAction = {
      for {
        user <- userTable
        userGroup <- userGroupTable
        group <- groupTable
        if user.id === userGroup.userId
        if userGroup.groupId === group.id
        if group.id === groupId
      } yield(user, group)
    }.result
    dataBase.run(getUsersByGroupIdAction)
  }

  def getGroupsByUserId(userId: Int): Future[Seq[(Group, User)]] = {
    val getGroupsByUserIdAction = {
      for {
        group <- groupTable
        userGroup <- userGroupTable
        user <- userTable
        if group.id === userGroup.groupId
        if userGroup.userId === user.id
        if user.id === userId
      } yield(group, user)
    }.result
    dataBase.run(getGroupsByUserIdAction)
  }
}