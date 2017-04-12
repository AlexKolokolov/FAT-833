package org.kolokolov.scalatra.controller

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
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
    logger.debug("get /users/1 is running")
    Try {
      params("id").toInt
    } match {
      case Success(id) => userActor ? UserById(id)
      case Failure(ex) => pass
    }
  }

  // shows all users in group with given ID
  get("/group/:gid") {
    Try {
      params("gid").toInt
    } match {
      case Success(groupId) => userActor ? UsersByGroupId(groupId)
      case Failure(ex) => pass
    }
  }

  // adds new user
  post("/") {
    Try {
      parsedBody.extract[User]
    } match {
      case Success(user) => userActor ! SaveUser(user)
      case Failure(ex) => pass
    }
  }

  // adds user with given ID to group with given ID
  post("/:uid/:gid") {
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield userActor ! AddUserToGroup(userId, groupId)
  }

  // removes user wiht given ID
  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => userActor ! DeleteUser(id)
      case Failure(ex) => pass
    }
  }

  // removes user with given ID from group with given ID
  delete("/:uid/:gid") {
    logger.debug("delete user from group is running")
    for {
      userId <- Try(params("uid").toInt)
      groupId <- Try(params("gid").toInt)
    } yield userActor ! DeleteUserFromGroup(userId, groupId)
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
    case saveUser: SaveUser => userService.saveUser(saveUser.user)
    case deleteUser: DeleteUser => userService.deleteUser(deleteUser.userId)
    case addUserToGroup: AddUserToGroup => userGroupService.addUserToGroup(addUserToGroup.userId, addUserToGroup.groupId)
    case deleteUserFromGroup: DeleteUserFromGroup => userGroupService.deleteUserFromGroup(deleteUserFromGroup.userId, deleteUserFromGroup.groupId)
  }
}