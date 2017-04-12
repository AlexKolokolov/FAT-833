package org.kolokolov.scalatra.controller

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask

import scala.concurrent.duration._
import akka.util.Timeout
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserController(system: ActorSystem)
  extends ScalatraServlet
    with JacksonJsonSupport
    with FutureSupport {

  this: DatabaseProfile =>

  private val logger = LoggerFactory.getLogger(classOf[UserController])

  override protected implicit def executor: ExecutionContext = system.dispatcher

  override protected implicit def jsonFormats: Formats = DefaultFormats

  protected lazy val userService = new UserService(profile)
  protected lazy val userGroupService = new UserGroupService(profile)

  private lazy val userActor = system.actorOf(Props(new UserActor(userService, userGroupService)))
  implicit val timeout = new Timeout(2 seconds)

  before() {
    contentType = formats("json")
  }

  // shows all users
  get("/") {
    logger.debug("get /users is running")
    userActor ? AllUsers
  }

  // shows user with given ID
  get("/:id") {
    logger.debug("get /users/:id is running")
    val userId = params("id")
    Try {
      userId.toInt
    } match {
      case Success(id) => userActor ? UserById(id)
      case Failure(ex) => BadRequest(s"Illegal parameter '$userId'")
    }
  }

  // shows all users in group with given ID
  get("/group/:gid") {
    val groupId = params("gid")
    Try {
      groupId.toInt
    } match {
      case Success(id) => userActor ? UsersByGroupId(id)
      case Failure(ex) => BadRequest(s"Illegal parameter '$groupId'")
    }
  }

  // adds new user
  post("/") {
    Try {
      parsedBody.extract[User]
    } match {
      case Success(user) => {
        userActor ? SaveUser(user)
        Accepted()
      }
      case Failure(ex) => BadRequest("Cannot extract user from request body")
    }
  }

  // adds user with given ID to group with given ID
  post("/:uid/:gid") {
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield {
      userActor ? AddUserToGroup(userId, groupId)
      Accepted()
    }
  }

  // removes user wiht given ID
  delete("/:id") {
    val userId = params("id")
    Try {
      userId.toInt
    } match {
      case Success(id) => {
        userActor ? DeleteUser(id)
        Accepted()
      }
      case Failure(ex) => BadRequest(s"Illegal parameter '$userId'")
    }
  }

  // removes user with given ID from group with given ID
  delete("/:uid/:gid") {
    logger.debug("delete user from group is running")
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield {
      userActor ? DeleteUserFromGroup(userId, groupId)
      Accepted()
    }
  }
}

case object AllUsers
case class SaveUser(user: User)
case class UserById(userId: Int)
case class UsersByGroupId(userId: Int)
case class DeleteUser(userId: Int)
case class AddUserToGroup(userId: Int, groupId: Int)
case class DeleteUserFromGroup(userId: Int, groupId: Int)

class UserActor(val userService: UserService, val userGroupService: UserGroupService) extends Actor {
  override def receive: Receive = {
    case AllUsers => sender ! userService.getAllUsers
    case userById: UserById => sender ! userService.getUserById(userById.userId)
    case usersByGroupId: UsersByGroupId => sender ! userGroupService.getUsersByGroupId(usersByGroupId.userId)
    case saveUser: SaveUser => sender ! userService.saveUser(saveUser.user)
    case deleteUser: DeleteUser => sender ! userService.deleteUser(deleteUser.userId)
    case addUserToGroup: AddUserToGroup => sender ! userGroupService.addUserToGroup(addUserToGroup.userId, addUserToGroup.groupId)
    case deleteUserFromGroup: DeleteUserFromGroup => sender ! userGroupService.deleteUserFromGroup(deleteUserFromGroup.userId, deleteUserFromGroup.groupId)
  }
}