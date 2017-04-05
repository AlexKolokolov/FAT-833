package org.kolokolov.slick.service

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.crud.{GroupCRUDModule, UserCRUDModule, UserGroupCRUDModule}
import org.kolokolov.slick.model.{Group, User}

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class UserGroupService extends UserGroupCRUDModule with UserCRUDModule with GroupCRUDModule  {

  this: DatabaseProfile =>

  def addUserToGroup(userId: Int, groupId: Int): Future[Int] = UserGroupCRUD.addUserToGroup(userId, groupId)

  def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = UserGroupCRUD.deleteUserFromGroup(userId, groupId)

  def getUsersByGroupId(groupId: Int): Future[Seq[(User,Group)]] = UserGroupCRUD.getUsersByGroupId(groupId)

  def getGroupsByUserId(userId: Int): Future[Seq[(Group,User)]] = UserGroupCRUD.getGroupsByUserId(userId)
}
