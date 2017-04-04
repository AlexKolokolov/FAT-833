package org.kolokolov.slick.service

import org.kolokolov.slick.crud.GroupCRUDModule
import org.kolokolov.slick.model.Group
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class GroupService(override protected val profile: JdbcProfile) {

  self: GroupCRUDModule =>

  private val groupCRUD = new GroupCRUD

  def getAllGroups: Future[Seq[Group]] = groupCRUD.getAll

  def getGroupById(groupId: Int): Future[Option[Group]] = groupCRUD.getById(groupId)

  def saveGroup(group: Group): Future[Int] = groupCRUD.save(group)

  def deleteGroup(groupId: Int): Future[Int] = groupCRUD.deleteById(groupId)
}
