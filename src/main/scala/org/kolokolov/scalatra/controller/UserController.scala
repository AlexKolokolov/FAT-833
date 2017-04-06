package org.kolokolov.scalatra.controller

import akka.actor.ActorSystem
import dispatch.Future
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.{DatabaseProfile, PostgresDatabase}
import org.kolokolov.slick.model.{Group, User}
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalatra.{AsyncResult, FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserController(system: ActorSystem)
  extends ScalatraServlet
    with JacksonJsonSupport
    with FutureSupport {

  this: DatabaseProfile =>

  override protected implicit def executor: ExecutionContext = system.dispatcher

  override protected implicit def jsonFormats: Formats = DefaultFormats

  private lazy val userService = new UserService(profile)
  private lazy val userGroupService = new UserGroupService(profile)

  before() {
    contentType = formats("json")
  }

  // shows all users
  get("/") {
    new AsyncResult {
      override val is: Future[Seq[User]] = userService.getAllUsers
    }
  }

  // shows user with given ID
  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => new AsyncResult {
        override val is: Future[Option[User]] = userService.getUserById(id)
      }
      case Failure(ex) => pass
    }
  }

  // shows all users in group with given ID
  get("/group/:gid") {
    Try {
      params("gid").toInt
    } match {
      case Success(groupId) => new AsyncResult {
        override val is: Future[Seq[(User, Group)]] = userGroupService.getUsersByGroupId(groupId)
      }
      case Failure(ex) => pass
    }
  }

  // adds new user
  post("/") {
    Try {
      parsedBody.extract[User]
    } match {
      case Success(user) => userService.saveUser(user)
      case Failure(ex) => pass
    }
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