package org.kolokolov.slick.service

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.crud.GroupCRUDModule
import org.kolokolov.slick.model.Group

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class GroupService extends GroupCRUDModule {

  this: DatabaseProfile =>

  def getAllGroups: Future[Seq[Group]] = GroupCRUD.getAll

  def getGroupById(groupId: Int): Future[Option[Group]] = GroupCRUD.getById(groupId)

  def saveGroup(group: Group): Future[Int] = GroupCRUD.save(group)

  def deleteGroup(groupId: Int): Future[Int] = GroupCRUD.deleteById(groupId)
}