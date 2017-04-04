package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User, UserGroup}

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
trait UserGroupCRUDModule extends AbstractCRUDModule {

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user_table") with TableHasId[User] {
    def id = column[Int]("user_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("user_name")
    def * = (name, id) <> (User.tupled, User.unapply)
  }

  protected lazy val userTable = TableQuery[UserTable]

  class GroupTable(tag: Tag) extends Table[Group](tag, "group_table") with TableHasId[Group] {
    def id = column[Int]("group_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("group_name")
    def * = (name, id) <> (Group.tupled, Group.unapply)
  }

  protected lazy val groupTable = TableQuery[GroupTable]

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

  class UserCRUD extends AbstractCRUD[User, UserTable] {
    override protected lazy val dataTable: TableQuery[UserTable] = userTable

    def getUsersByGroupId(groupId: Int): Future[Seq[(User, Group)]] = {
      val getUsersByGroupIdAction = {
        for {
          user <- dataTable
          userGroup <- userGroupTable
          group <- groupTable
          if user.id === userGroup.userId
          if userGroup.groupId === group.id
          if group.id === groupId
        } yield(user, group)
      }.result
      dataBase.run(getUsersByGroupIdAction)
    }

    def addUserToGroup(userId: Int, groupId: Int): Future[Int] = {
      val addUserToGroupAction = userGroupTable += UserGroup(userId,groupId)
      dataBase.run(addUserToGroupAction)
    }

    def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = {
      val deleteUserFromGroupAction = userGroupTable.filter(_.userId === userId)
        .filter(_.groupId === groupId).delete
      dataBase.run(deleteUserFromGroupAction)
    }
  }

  class GroupCRUD extends AbstractCRUD[Group, GroupTable] {
    override protected lazy val dataTable: TableQuery[GroupTable] = groupTable

    def getGroupsByUserId(userId: Int): Future[Seq[(Group, User)]] = {
      val getGroupsByUserIdAction = {
        for {
          group <- dataTable
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
}