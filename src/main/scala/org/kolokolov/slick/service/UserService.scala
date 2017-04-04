package org.kolokolov.slick.service

import org.kolokolov.slick.crud.UserCRUDModule
import org.kolokolov.slick.model.User

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserService {

  this: UserCRUDModule =>

  private val userCRUD = new UserCRUD

  def getAllUsers: Future[Seq[User]] = userCRUD.getAll

  def getUserById(userId: Int): Future[Option[User]] = userCRUD.getById(userId)

  def saveUser(user: User): Future[Int] = userCRUD.save(user)

  def deleteUser(userId: Int): Future[Int] = userCRUD.deleteById(userId)
}
