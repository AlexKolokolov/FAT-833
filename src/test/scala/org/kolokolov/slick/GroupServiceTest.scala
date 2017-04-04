package org.kolokolov.slick

import org.kolokolov.slick.crud.GroupCRUDModule
import org.kolokolov.slick.model.Group
import org.kolokolov.slick.service.GroupService
import org.scalatest.{AsyncFunSuite, BeforeAndAfterEach, Matchers}
import slick.jdbc.H2Profile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kolokolov on 3/31/17.
  */
class GroupServiceTest extends AsyncFunSuite
  with Matchers
  with BeforeAndAfterEach {

  private val groupService = new GroupService(H2Profile) with GroupCRUDModule

  private val testDataBaseManager = new TestDataBaseManager(H2Profile)

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("getGroupById(2) should return Group(Admin, 2)") {
    groupService.getGroupById(2).map {
      result => result shouldEqual Some(Group("Admin",2))
    }
  }

  test("getAllGroups should return Seq(Group(User,1), Group(Admin, 2))") {
    groupService.getAllGroups.map {
      result => result shouldEqual Seq(Group("User",1), Group("Admin",2))
    }
  }

  test("getGroupById(1) should return None after deleteGroup(1)") {
    groupService.deleteGroup(1).flatMap {
      delRes => {
        delRes shouldEqual 1
        groupService.getGroupById(1).map {
          result => result shouldEqual None
        }
      }
    }
  }

  test("getAllGroups.length should return 3") {
    groupService.saveGroup(Group("Developer")).flatMap(
      addRes => {
        addRes shouldEqual 1
        groupService.getAllGroups.map {
          result => result.length shouldEqual 3
        }
      }
    )
  }
}