package org.kolokolov.scalatra.integration

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.GroupController
import org.kolokolov.slick.DBprofiles.H2Database
import org.kolokolov.slick.TestDataBaseManager
import org.scalatest.{BeforeAndAfterEach, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
class GroupControllerTest extends ScalatraSuite
  with FunSuiteLike
  with BeforeAndAfterEach {

  val system = ActorSystem()

  private val testDataBaseManager = new TestDataBaseManager with H2Database

  addServlet(new GroupController(system) with H2Database, "/groups")

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("GET /groups/1 should return {name:User,id:1}") {
    get("/groups/1") {
      body should include ("User")
    }
  }

  test("GET /groups should return [{name:User,id:1},{name:Admin,id:2}]") {
    get("/groups") {
      body should include ("Admin")
    }
  }
}