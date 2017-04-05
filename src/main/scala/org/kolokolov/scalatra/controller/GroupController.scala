package org.kolokolov.scalatra.controller

import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.PostgresDatabase
import org.kolokolov.slick.model.Group
import org.kolokolov.slick.service.{GroupService, UserGroupService}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class GroupController extends ScalatraServlet with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  val groupService = new GroupService with PostgresDatabase
  val userGroupService = new UserGroupService with PostgresDatabase

  before() {
    contentType = formats("json")
  }

  // shows all groups
  get("/") {
    Await.result(groupService.getAllGroups, Duration(2, "sec"))
  }

  // shows group with given ID
  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => Await.result(groupService.getGroupById(id), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  // shows all groups of user with given ID
  get("/user/:uid") {
    Try {
      params("uid").toInt
    } match {
      case Success(userId) => Await.result(userGroupService.getGroupsByUserId(userId), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  // adds new group
  post("/") {
    val group = parsedBody.extract[Group]
    groupService.saveGroup(group)
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