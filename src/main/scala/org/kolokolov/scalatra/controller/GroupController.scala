package org.kolokolov.scalatra.controller

import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.PostgresDatabase
import org.kolokolov.slick.model.Group
import org.kolokolov.slick.service.GroupService
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

  before() {
    contentType = formats("json")
  }

  get("/") {
    Await.result(groupService.getAllGroups, Duration(2, "sec"))
  }

  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => Await.result(groupService.getGroupById(id), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  post("/") {
    val group = parsedBody.extract[Group]
    groupService.saveGroup(group)
  }

  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => groupService.deleteGroup(id)
      case Failure(ex) => pass
    }
  }
}
