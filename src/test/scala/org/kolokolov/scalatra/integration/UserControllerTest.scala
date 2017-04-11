package org.kolokolov.scalatra.integration

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.UserController
import org.kolokolov.slick.DBprofiles.H2Database
import org.kolokolov.slick.TestDataBaseManager
import org.scalatest.{AsyncFunSuiteLike, BeforeAndAfterEach}
import org.scalatra.test.scalatest.ScalatraSuite

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
class UserControllerTest extends ScalatraSuite
  with AsyncFunSuiteLike
  with BeforeAndAfterEach {

  val system = ActorSystem()

  private val testDataBaseManager = new TestDataBaseManager with H2Database

  addServlet(new UserController(system) with H2Database, "/users")

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("GET /users/1 should return {name:Bob Marley,id:1}") {
    get("/users/1") {
      body should include ("Bob Marley")
    }
  }

  test("GET /users should return [{name:Bob Marley,id:1},{name:Ron Perlman,id:2},{name:Tom Waits,id:3}]") {
    get("/users") {
      body should include ("[{\"name\":\"Bob Marley\",\"id\":1},{\"name\":\"Ron Perlman\",\"id\":2},{\"name\":\"Tom Waits\",\"id\":3}]")
    }
  }

  test("GET /users/group/2 should return [{_1:{name:Tom Waits,id:3},_2:{name:Admin,id:2}}]") {
    get("/users/group/2") {
      body should include ("[{\"_1\":{\"name\":\"Tom Waits\",\"id\":3},\"_2\":{\"name\":\"Admin\",\"id\":2}}]")
    }
  }

  test("GET /users should return [{name:Bob Marley,id:1},{name:Ron Perlman,id:2}] after DELETE /users/3") {
    delete("/users/3") {
      get("/users") {
        body should not include "Tom Waits"
      }
    }
  }

  test("GET /users/group/2 should return [] after DELETE /users/3/2") {
    delete("/users/3/2") {
      get("/users/group/2") {
        body should include ("[]")
      }
    }
  }

  test("GET /users/group/2 should include {name:Ron Perlman,id:2} after POST /users/2/2") {
    post("/users/2/2") {
      get("/users/group/2") {
        body should include ("Ron Perlman")
      }
    }
  }

  test("GET /users should include {name:Elvis Presley,id:4} after POST /users with {name:Elvis Presley,id:0}") {
    post("/users","{\"name\":\"Elvis Presley\",\"id\":0}") {
      get("/users") {
        body should include ("Elvis Presley")
      }
    }
  }
}