package org.kolokolov.scalatra.controller

import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.DBprofiles.PostgresDatabase
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.UserService
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

  before() {
    contentType = formats("json")
  }

  get("/") {
    Await.result(userService.getAllUsers, Duration(2, "sec"))
  }

  get("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => Await.result(userService.getUserById(id), Duration(2, "sec"))
      case Failure(ex) => pass
    }
  }

  post("/") {
    val user = parsedBody.extract[User]
    userService.saveUser(user)
  }

  delete("/:id") {
    Try {
      params("id").toInt
    } match {
      case Success(id) => userService.deleteUser(id)
      case Failure(ex) => pass
    }
  }
}
