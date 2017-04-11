package org.kolokolov.scalatra.unit

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.UserController
import org.kolokolov.slick.DBprofiles.H2Database
import org.kolokolov.slick.model.{Group, User}
import org.kolokolov.slick.service.{UserGroupService, UserService}
import org.scalamock.scalatest.MockFactory
import org.scalatest.AsyncFunSuiteLike
import org.scalatra.test.scalatest.ScalatraSuite
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
class UserControllerTest extends ScalatraSuite
  with MockFactory
  with AsyncFunSuiteLike {

  private val logger = LoggerFactory.getLogger(this.getClass)

  val system = ActorSystem()

  val firstUser = User("Bob Marley",1)
  val secondUser = User("Tom Waits",2)
  val group = Group("Admin",1)

  private val userServiceStub = stub[UserService]
  (userServiceStub.getUserById _).when(firstUser.id).returns(Future(Some(firstUser)))
  (userServiceStub.getAllUsers _).when().returns(Future(Seq(firstUser,secondUser)))

  private val userGroupServiceStub = stub[UserGroupService]
  (userGroupServiceStub.getUsersByGroupId _).when(group.id).returns(Future(Seq((firstUser,group))))

  class MockedUserController extends UserController(system) with H2Database {
    override protected lazy val userService: UserService = userServiceStub
    override protected lazy val userGroupService: UserGroupService = userGroupServiceStub
  }

  addServlet(new MockedUserController, "/users")

  test("GET /users/ should return [{name:Bob Marley,id:1},{name:Tom Waits,id:2}]") {
    get("/users") {
      body should include (secondUser.name)
    }
  }

  test("GET /users/1 should return {name:Bob Marley,id:1}") {
    logger.debug("get /users/1 test is running")
    get("/users/1") {
      body should include (firstUser.name)
    }
  }

  test("GET /users/group/1 should return [{_1:{name:Bob Marley,id:1},_2:{name:Admin,id:1}}]") {
    logger.debug("get /users/group/1 test is running")
    get("/users/group/1") {
      body should include (firstUser.name)
    }
  }
}