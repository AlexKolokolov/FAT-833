package org.kolokolov.scalatra.controller

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.pattern.pipe
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration._
import akka.util.Timeout
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.{Error, User}
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalatra._
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
    logger.debug("get /users/:id is running")
    val id = params("id")
    Try {
      id.toInt
    } match {
      case Success(userId) => userActor ? UserById(userId) map {
        case Some(user) => Ok(user)
        case None => NotFound(Error(s"User with id: $id was not found"))
      }
      case Failure(ex) => BadRequest(Error(s"Illegal parameter '$id'"))
    }
  }

  // shows all users in group with given ID
  get("/group/:gid") {
    val gId = params("gid")
    Try {
      gId.toInt
    } match {
      case Success(groupId) => userActor ? UsersByGroupId(groupId)
      case Failure(ex) => BadRequest(Error(s"Illegal parameter '$gId'"))
    }
  }

  // adds new user
  post("/") {
    Try {
      parsedBody.extract[User]
    } match {
      case Success(user) => userActor ? SaveUser(user) map {
        case 1 => Accepted()
        case _ => Conflict(Error(s"User $user was not persisted to database"))
      }
      case Failure(ex) => BadRequest(Error("Cannot extract user from request body"))
    }
  }

  // adds user with given ID to group with given ID
  post("/:uid/:gid") {
    val uid = params("uid")
    Try {
      uid.toInt
    } match {
      case Success(userId) => {
        val gid = params("gid")
        Try {
          gid.toInt
        } match {
          case Success(groupId) => userActor ? AddUserToGroup(userId,groupId) map {
            case 1 => Accepted()
            case _ => Conflict(Error(s"User with id: $userId was not added to group with id: $groupId"))
          }
          case Failure(ex) => BadRequest(Error(s"Illegal parameter '$gid'"))
        }
      }
      case Failure(ex) => BadRequest(Error(s"Illegal parameter '$uid'"))
    }
  }

  // removes user with given ID
  delete("/:id") {
    val id = params("id")
    Try {
      id.toInt
    } match {
      case Success(userId) => userActor ? DeleteUser(userId) map {
        case 1 => Accepted()
        case _ => NotFound(Error(s"User with id: $userId was not found"))
      }
      case Failure(ex) => BadRequest(Error(s"Illegal parameter '$id'"))
    }
  }

  // removes user with given ID from group with given ID
  delete("/:uid/:gid") {
    logger.debug("delete user from group is running")
    val uid = params("uid")
    Try {
      uid.toInt
    } match {
      case Success(userId) => {
        val gid = params("gid")
        Try {
          gid.toInt
        } match {
          case Success(groupId) => userActor ? DeleteUserFromGroup(userId,groupId) map {
            case 1 => Accepted()
            case _ => Conflict(Error(s"User with id: $userId was not found in group with id: $groupId"))
          }
          case Failure(ex) => BadRequest(Error(s"Illegal parameter '$gid'"))
        }
      }
      case Failure(ex) => BadRequest(Error(s"Illegal parameter '$uid'"))
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
    case AllUsers => userService.getAllUsers.pipeTo(sender)
    case userById: UserById => userService.getUserById(userById.userId).pipeTo(sender)
    case usersByGroupId: UsersByGroupId => userGroupService.getUsersByGroupId(usersByGroupId.userId).pipeTo(sender)
    case saveUser: SaveUser => userService.saveUser(saveUser.user).pipeTo(sender)
    case deleteUser: DeleteUser => userService.deleteUser(deleteUser.userId).pipeTo(sender)
    case addUserToGroup: AddUserToGroup => userGroupService.addUserToGroup(addUserToGroup.userId, addUserToGroup.groupId).pipeTo(sender)
    case deleteUserFromGroup: DeleteUserFromGroup => userGroupService.deleteUserFromGroup(deleteUserFromGroup.userId, deleteUserFromGroup.groupId).pipeTo(sender)
  }
}