package org.kolokolov.slick.repo

import slick.jdbc.PostgresProfile.api._
import org.kolokolov.slick.domain._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
object GroupRepo {
  private val db = Database.forConfig("db.config")
  private lazy val userTable = TableQuery[UserTable]
  private lazy val groupTable = TableQuery[GroupTable]
  private lazy val userGroupTable = TableQuery[UserGroupTable]

  def save(group: Group): Unit = {
    db.run(groupTable += group)
  }

  def getAllGroups: Seq[Group] = {
    val allGroups = groupTable.result
    Await.result(db.run(allGroups), Duration(2, "second"))
  }

  def getGroupById(id: Int): Option[Group] = {
    val groupById = groupTable.filter(_.id === id).result
    Await.result(db.run(groupById), Duration(2, "second")).headOption
  }

  def deleteGroupById(id: Int): Unit = {
    val deleteGroupById = groupTable.filter(_.id === id).delete
    db.run(deleteGroupById)
  }

  def getGroupsByUserId(userId: Int): Seq[(Group, User)] = {
    val groupsByUserId = {
      for {
        group <- groupTable
        userGroup <- userGroupTable
        user <- userTable
        if group.id === userGroup.groupId
        if userGroup.userId === user.id
        if user.id === userId
      } yield(group, user))
    } result
    Await.result(db.run(groupsByUserId), Duration(2, "second"))
  }
}
