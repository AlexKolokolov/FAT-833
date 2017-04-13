package org.kolokolov.slick.service

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.crud.{GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import org.kolokolov.slick.model.{Group, User}
import org.slf4j.LoggerFactory
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class UserGroupService(override protected val profile: JdbcProfile)
  extends UserGroupCRUDModule
    with UserCRUDModule
    with GroupCRUDModule
    with DatabaseProfile {

  private val logger = LoggerFactory.getLogger(classOf[UserGroupService])

  def addUserToGroup(userId: Int, groupId: Int): Future[Int] = {
    val result = UserGroupCRUD.addUserToGroup(userId, groupId)
    result.onComplete(lines => logger.debug("Lines modified: {}", lines.get))
    result
  }

  def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = UserGroupCRUD.deleteUserFromGroup(userId, groupId)

  def getUsersByGroupId(groupId: Int): Future[Seq[(User,Group)]] = UserGroupCRUD.getUsersByGroupId(groupId)

  def getGroupsByUserId(userId: Int): Future[Seq[(Group,User)]] = UserGroupCRUD.getGroupsByUserId(userId)
}
