package org.kolokolov.slick.crud

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.{Group, User, UserGroup}

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 04.04.2017.
  */
trait UserGroupCRUDModule {

  self: UserCRUDModule with GroupCRUDModule with DatabaseProfile =>

  import profile.api._

  class UserGroupTable(tag: Tag) extends Table[UserGroup](tag, "user_group_table") {
    def userId = column[Int]("user_id")
    def groupId = column[Int]("group_id")
    def * = (userId, groupId) <> (UserGroup.tupled, UserGroup.unapply)
    def pk = primaryKey("primary_key", (userId, groupId))
    def user = foreignKey("user_fk", userId, UserCRUD.dataTable)(_.id,
      onDelete = ForeignKeyAction.Cascade)
    def group = foreignKey("group_fk", groupId, GroupCRUD.dataTable)(_.id,
      onDelete = ForeignKeyAction.Cascade)
  }

  object UserGroupCRUD {

    lazy val dataTable = TableQuery[UserGroupTable]
    lazy val userTable = UserCRUD.dataTable
    lazy val groupTable = GroupCRUD.dataTable

    def addUserToGroup(userId: Int, groupId: Int): Future[Int] = {
      val addUserToGroupAction = dataTable += UserGroup(userId,groupId)
      dataBase.run(addUserToGroupAction)
    }

    def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = {
      val deleteUserFromGroupAction = dataTable.filter(_.userId === userId)
        .filter(_.groupId === groupId).delete
      dataBase.run(deleteUserFromGroupAction)
    }

    def getUsersByGroupId(groupId: Int): Future[Seq[(User, Group)]] = {
      val getUsersByGroupIdAction = {
        for {
          user <- userTable
          userGroup <- dataTable
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
          userGroup <- dataTable
          user <- userTable
          if group.id === userGroup.groupId
          if userGroup.userId === user.id
          if user.id === userId
        } yield(group, user)
      }.result
      dataBase.run(getGroupsByUserIdAction)
    }
  }
}