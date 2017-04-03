package org.kolokolov.scalatra.controller

import org.json4s.{DefaultFormats, Formats}
import org.kolokolov.slick.repo.UserRepo
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import slick.jdbc.PostgresProfile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
class UserController extends ScalatraServlet with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  val userRepo = new UserRepo(PostgresProfile)

  before() {
    contentType = formats("json")
  }

  get("/") {
    Await.result(userRepo.getAll, Duration(2, "sec"))
  }
}
