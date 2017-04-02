package org.kolokolov.slick.repo

import org.kolokolov.slick.domain._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
class GroupRepo(override val profile: JdbcProfile) extends Repo[Group] {

  import profile.api._

  override protected val dataTable: TableQuery[Table[Group]] = groupTable.asInstanceOf[TableQuery[Table[Group]]]

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