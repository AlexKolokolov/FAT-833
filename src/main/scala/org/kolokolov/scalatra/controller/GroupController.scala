package org.kolokolov.scalatra.controller

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.Group
import org.kolokolov.slick.service.{GroupService, UserGroupService}
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class GroupController(system: ActorSystem)
  extends ScalatraServlet
    with JacksonJsonSupport
    with FutureSupport {

  this: DatabaseProfile =>

  override protected implicit def executor: ExecutionContext = system.dispatcher

  override protected implicit def jsonFormats: Formats = DefaultFormats

  protected lazy val groupService = new GroupService(profile)
  protected lazy val userGroupService = new UserGroupService(profile)

  private lazy val groupActor = system.actorOf(Props(new GroupActor(groupService, userGroupService)))

  implicit val timeout = new Timeout(2 seconds)

  before() {
    contentType = formats("json")
  }

  // shows all groups
  get("/") {
    groupActor ? AllGroups
  }

  // shows group with given ID
  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => groupActor ? GroupById(id)
      case Failure(ex) => pass
    }
  }

  // shows all groups of user with given ID
  get("/user/:uid") {
    Try {
      params("uid").toInt
    } match {
      case Success(userId) => groupActor ? GroupsByUserId(userId)
      case Failure(ex) => pass
    }
  }

  // adds new group
  post("/") {
    Try {
      parsedBody.extract[Group]
    } match {
      case Success(group) => groupActor ! SaveGroup(group)
      case Failure(ex) => pass
    }
  }

  // removes group with given ID
  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => groupActor ! DeleteGroup(id)
      case Failure(ex) => pass
    }
  }
}

case object AllGroups
case class SaveGroup(group: Group)
case class GroupById(groupId: Int)
case class GroupsByUserId(userId: Int)
case class DeleteGroup(groupId: Int)

class GroupActor(val groupService: GroupService, val userGroupService: UserGroupService) extends Actor {
  override def receive: Receive = {
    case AllGroups => sender ! groupService.getAllGroups
    case groupById: GroupById => sender ! groupService.getGroupById(groupById.groupId)
    case groupByUserId: GroupsByUserId => sender ! userGroupService.getGroupsByUserId(groupByUserId.userId)
    case saveGroup: SaveGroup => groupService.saveGroup(saveGroup.group)
    case deleteGroup: DeleteGroup => groupService.deleteGroup(deleteGroup.groupId)
  }
}