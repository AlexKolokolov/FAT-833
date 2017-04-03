package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserRepo(override val profile: JdbcProfile) extends UserGroupCRUDModule {

  private val userCRUD = new UserCRUD

  def getAllUsers: Future[Seq[User]] = userCRUD.getAll

  def getUserById(userId: Int): Future[Option[User]] = userCRUD.getById(userId)

  def getUsersByGroupId(groupId: Int): Future[Seq[(User,Group)]] = userCRUD.getUsersByGroupId(groupId)

  def saveUser(user: User): Future[Int] = userCRUD.save(user)

  def addUserToGroup(userId: Int, groupId: Int): Future[Int] = userCRUD.addUserToGroup(userId,groupId)

  def deleteUserFromGroup(userId: Int, groupId: Int): Future[Int] = userCRUD.deleteUserFromGroup(userId,groupId)

  def deleteUser(userId: Int): Future[Int] = userCRUD.deleteById(userId)
}
