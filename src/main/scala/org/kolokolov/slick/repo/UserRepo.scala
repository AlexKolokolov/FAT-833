package org.kolokolov.slick.repo

import slick.jdbc.PostgresProfile.api._
import org.kolokolov.slick.domain._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
object UserRepo {
  private val db = Database.forConfig("db.config")
  private lazy val userTable = TableQuery[UserTable]
  private lazy val groupTable = TableQuery[GroupTable]
  private lazy val userGroupTable = TableQuery[UserGroupTable]

  def save(user: User): Unit = {
    db.run(userTable += user)
  }

  def addUserToGroup(userId: Int, groupId: Int): Option[(User,Group)] = {
    db.run(userGroupTable += UserGroup(userId,groupId))
    val userInGroup =
    for {
      userGroup <- userGroupTable
      user <- userTable
      group <- groupTable
      if userGroup.groupId === groupId
      if userGroup.userId === userId
      if user.id === userGroup.userId
      if group.id === userGroup.groupId
    } yield (user, group)
    Await.result(db.run(userInGroup.result), Duration(2, "second")).headOption
  }

  def getAllUsers: Seq[User] = {
    val allUsers = userTable.result
    Await.result(db.run(allUsers), Duration(2, "second"))
  }

  def getUserById(id: Int): Option[User] = {
    val userById = userTable.filter(_.id === id).result
    Await.result(db.run(userById), Duration(2, "second")).headOption
  }

  def deleteUserById(id: Int): Unit = {
    val userById = userTable.filter(_.id === id).delete
    db.run(userById)
  }

  def getUsersByGroupId(groupId: Int): Seq[(User, Group)] = {
    val usersByGroupId = {
      for {
        user <- userTable
        userGroup <- userGroupTable
        group <- groupTable
        if user.id === userGroup.userId
        if userGroup.groupId === group.id
        if group.id === groupId
      } yield (user, group)
    } result
    Await.result(db.run(usersByGroupId), Duration(2, "second"))
  }
}
