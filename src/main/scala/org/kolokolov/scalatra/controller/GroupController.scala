package org.kolokolov.scalatra.controller

import akka.actor.ActorSystem
import dispatch.Future
import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.{DatabaseProfile, PostgresDatabase}
import org.kolokolov.slick.model.{Group, User}
import org.kolokolov.slick.service.{GroupService, UserGroupService}
import org.scalatra.{AsyncResult, FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

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

  before() {
    contentType = formats("json")
  }

  // shows all groups
  get("/") {
    new AsyncResult {
      override val is: Future[Seq[Group]] = groupService.getAllGroups
    }
  }

  // shows group with given ID
  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => new AsyncResult {
        override val is: Future[Option[Group]] = groupService.getGroupById(id)
      }
      case Failure(ex) => pass
    }
  }

  // shows all groups of user with given ID
  get("/user/:uid") {
    Try {
      params("uid").toInt
    } match {
      case Success(userId) => new AsyncResult {
        override val is: Future[Seq[(Group, User)]] = userGroupService.getGroupsByUserId(userId)
      }
      case Failure(ex) => pass
    }
  }

  // adds new group
  post("/") {
    Try {
      parsedBody.extract[Group]
    } match {
      case Success(group) => groupService.saveGroup(group)
      case Failure(ex) => pass
    }
  }

  // removes group with given ID
  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => groupService.deleteGroup(id)
      case Failure(ex) => pass
    }
  }
}