package org.kolokolov.slick.repo

import org.kolokolov.slick.domain._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 28.03.2017.
  */
class UserRepo(override val profile: JdbcProfile) extends Repo[User] {

  import profile.api._

  override protected val dataTable: TableQuery[Table[User]] = userTable.asInstanceOf[TableQuery[Table[User]]]

  def addUserToGroup(userId: Int, groupId: Int): Future[Int] = {
    db.run(userGroupTable += UserGroup(userId,groupId))
  }

  def getUsersByGroupId(groupId: Int): Future[Seq[(User, Group)]] = {
    val usersByGroupId = {
      for {
        user <- userTable
        userGroup <- userGroupTable
        group <- groupTable
        if user.id === userGroup.userId
        if userGroup.groupId === group.id
        if group.id === groupId
      } yield(user, group)
    }.result
    db.run(usersByGroupId)
  }
}