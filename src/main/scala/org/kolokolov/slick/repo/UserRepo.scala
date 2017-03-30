package org.kolokolov.slick.repo

import org.kolokolov.slick.domain._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
class UserRepo(override val profile: JdbcProfile) extends UserGroupModule {

  import profile.api._

  private val db = Database.forConfig("db.config")

  def save(user: User): Unit = {
    db.run(userTable += user)
  }

  def addUserToGroup(userId: Int, groupId: Int): Future[Option[(User,Group)]] = {
    db.run(userGroupTable += UserGroup(userId,groupId))
    val userInGroup = {
      for {
        userGroup <- userGroupTable
        user <- userTable
        group <- groupTable
        if userGroup.groupId === groupId
        if userGroup.userId === userId
        if user.id === userGroup.userId
        if group.id === userGroup.groupId
      } yield (user, group)
    }.result.headOption
    db.run(userInGroup)
  }

  def getAllUsers: Future[Seq[User]] = {
    val allUsers = userTable.result
    db.run(allUsers)
  }

  def getUserById(id: Int): Future[Option[User]] = {
    val userById = userTable.filter(_.id === id).result.headOption
    db.run(userById)
  }

  def deleteUserById(id: Int): Unit = {
    val userById = userTable.filter(_.id === id).delete
    db.run(userById)
  }

  def getUsersByGroupId(groupId: Int): Future[Seq[(User, Group)]] = {
    val usersByGroupId = {
      for {
        user <- userTable
        userGroup <- userGroupTable
        group <- groupTable
        if user.id === userGroup.userId
        if userGroup.groupId === group.id
        if group.id === groupId
      } yield(user, group)
    }.result
    db.run(usersByGroupId)
  }
}
