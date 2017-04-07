package org.kolokolov.scalatra.unit

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.GroupController
import org.kolokolov.slick.DBprofiles.H2Database
import org.kolokolov.slick.model.Group
import org.kolokolov.slick.service.GroupService
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterEach, FunSuiteLike}
import org.scalatra.test.scalatest.ScalatraSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 06.04.2017.
  */
class GroupControllerTest extends ScalatraSuite
  with MockFactory
  with FunSuiteLike
  with BeforeAndAfterEach {

  private val system = ActorSystem()

  private val groupServiceStub = stub[GroupService]
  (groupServiceStub.getGroupById _).when(1).returns(Future(Some(Group("User",1))))
  (groupServiceStub.getAllGroups _).when().returns(Future(Seq(Group("User", 1),Group("Admin",2))))

  class MockedGroupController extends GroupController(system) with H2Database {
    override protected lazy val groupService: GroupService = groupServiceStub
  }

  addServlet(new MockedGroupController, "/groups")

  test("GET /groups/ should return [{name:User,id:1},{name:Admin,id:2}]") {
    get("/groups") {
      body should include ("Admin")
    }
  }

  ignore("GET /groups/1 should return {name:User,id:1}") {
    get("/groups/1") {
      body should include ("User")
    }
  }
}