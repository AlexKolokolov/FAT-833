package org.kolokolov.slick.service

import org.kolokolov.slick.crud.{DatabaseProfile, GroupCRUDModule}
import org.kolokolov.slick.model.Group
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class GroupService(override protected val profile: JdbcProfile) extends GroupCRUDModule with DatabaseProfile {

  def getAllGroups: Future[Seq[Group]] = GroupCRUD.getAll

  def getGroupById(groupId: Int): Future[Option[Group]] = GroupCRUD.getById(groupId)

  def saveGroup(group: Group): Future[Int] = GroupCRUD.save(group)

  def deleteGroup(groupId: Int): Future[Int] = GroupCRUD.deleteById(groupId)
}