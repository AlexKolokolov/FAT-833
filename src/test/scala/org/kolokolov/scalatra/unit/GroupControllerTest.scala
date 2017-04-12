package org.kolokolov.scalatra.unit

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.GroupController
import org.kolokolov.slick.DBprofiles.H2Database
import org.kolokolov.slick.model.{Group, User}
import org.kolokolov.slick.service.{GroupService, UserGroupService}
import org.scalamock.scalatest.MockFactory
import org.scalatest.AsyncFunSuiteLike
import org.scalatra.test.scalatest.ScalatraSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
class GroupControllerTest extends ScalatraSuite
  with MockFactory
  with AsyncFunSuiteLike {

  val system = ActorSystem()

  val firstGroup = Group("User",1)
  val secondGroup = Group("Admin",2)
  val user = User("Bob Marley", 1)

  private val groupServiceStub = stub[GroupService]
  (groupServiceStub.getGroupById _).when(1).returns(Future(Some(firstGroup)))
  (groupServiceStub.getAllGroups _).when().returns(Future(Seq(firstGroup,secondGroup)))

  private val userGroupServiceStub = stub[UserGroupService]
  (userGroupServiceStub.getGroupsByUserId _).when(user.id).returns(Future(Seq((firstGroup,user))))

  class MockedGroupController extends GroupController(system) with H2Database {
    override protected lazy val groupService: GroupService = groupServiceStub
    override protected lazy val userGroupService: UserGroupService = userGroupServiceStub
  }

  addServlet(new MockedGroupController, "/groups")

  test("GET /groups/ should return [{name:User,id:1},{name:Admin,id:2}]") {
    get("/groups") {
      body should include ("Admin")
    }
  }

  test("GET /groups/1 should return {name:User,id:1}") {
    get("/groups/1") {
      body should include ("User")
    }
  }

  test("GET /groups/user/1 should return [{_1:{name:User,id:1},_2:{name:Bob Marley,id:1}}]") {
    get("/groups/user/1") {
      body should include (firstGroup.name)
    }
  }
}