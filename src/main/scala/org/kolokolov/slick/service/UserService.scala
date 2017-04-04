package org.kolokolov.slick.service

import org.kolokolov.slick.crud.{DatabaseProfile, UserCRUDModule}
import org.kolokolov.slick.model.User

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserService extends UserCRUDModule {

  this: DatabaseProfile =>

  def getAllUsers: Future[Seq[User]] = UserCRUD.getAll

  def getUserById(userId: Int): Future[Option[User]] = UserCRUD.getById(userId)

  def saveUser(user: User): Future[Int] = UserCRUD.save(user)

  def deleteUser(userId: Int): Future[Int] = UserCRUD.deleteById(userId)
}
