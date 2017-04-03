package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class GroupRepo(override val profile: JdbcProfile) extends UserGroupCRUDModule {

  private val groupCRUD = new GroupCRUD

  def getAllGroups: Future[Seq[Group]] = groupCRUD.getAll

  def getGroupById(groupId: Int): Future[Option[Group]] = groupCRUD.getById(groupId)

  def getGroupsByUserId(userId: Int): Future[Seq[(Group,User)]] = groupCRUD.getGroupsByUserId(userId)

  def saveGroup(group: Group): Future[Int] = groupCRUD.save(group)

  def deleteGroup(groupId: Int): Future[Int] = groupCRUD.deleteById(groupId)
}
