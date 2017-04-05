package org.kolokolov.scalatra.controller

import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.PostgresDatabase
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserController extends ScalatraServlet with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  val userService = new UserService with PostgresDatabase
  val userGroupService = new UserGroupService with PostgresDatabase

  before() {
    contentType = formats("json")
  }

  // shows all users
  get("/") {
    Await.result(userService.getAllUsers, Duration(2, "sec"))
  }

  // shows user with given ID
  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => Await.result(userService.getUserById(id), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  // shows all users in group with given ID
  get("/group/:gid") {
    Try {
      params("gid").toInt
    } match {
      case Success(groupId) => Await.result(userGroupService.getUsersByGroupId(groupId), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  // adds new user
  post("/") {
    val user = parsedBody.extract[User]
    userService.saveUser(user)
  }

  // adds user with given ID to group with given ID
  post("/:uid/:gid") {
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield userGroupService.addUserToGroup(userId,groupId)
  }

  // removes user wiht given ID
  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => userService.deleteUser(id)
      case Failure(ex) => pass
    }
  }

  // removes user with given ID from group with given ID
  delete("/:uid/:gid") {
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield userGroupService.deleteUserFromGroup(userId,groupId)
  }
}