package org.kolokolov.slick.repo

import org.kolokolov.slick.domain._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
class GroupRepo(override val profile: JdbcProfile) extends UserGroupModule {

  import profile.api._

  private val db = Database.forConfig("db.config")

  def save(group: Group): Future[Int] = {
    db.run(groupTable += group)
  }

  def getAllGroups: Future[Seq[Group]] = {
    val allGroups = groupTable.result
    db.run(allGroups)
  }

  def getGroupById(id: Int): Future[Option[Group]] = {
    val groupById = groupTable.filter(_.id === id).result.headOption
    db.run(groupById)
  }

  def deleteGroupById(id: Int): Future[Int] = {
    val deleteGroupById = groupTable.filter(_.id === id).delete
    db.run(deleteGroupById)
  }

  def getGroupsByUserId(userId: Int): Future[Seq[(Group, User)]] = {
    val groupsByUserId = {
      for {
        group <- groupTable
        userGroup <- userGroupTable
        user <- userTable
        if group.id === userGroup.groupId
        if userGroup.userId === user.id
        if user.id === userId
      } yield(group, user)
    }.result
    db.run(groupsByUserId)
  }
}