package org.kolokolov.slick

import org.kolokolov.slick.crud.GroupRepo
import org.kolokolov.slick.model.{Group, User}
import org.scalatest.{AsyncFunSuite, BeforeAndAfterEach, Matchers}
import slick.jdbc.H2Profile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kolokolov on 3/31/17.
  */
class GroupRepoTest extends AsyncFunSuite
  with Matchers
  with BeforeAndAfterEach {

  private val testDataBaseManager = new TestDataBaseManager(H2Profile)
  private val groupRepo = new GroupRepo(H2Profile)

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("getGroupById(2) should return Group(Admin, 2)") {
    groupRepo.getGroupById(2).map {
      result => result shouldEqual Some(Group("Admin",2))
    }
  }

  test("getGroupsByUserId(3) should return Seq((Group(User, 1), User(Tom Waits, 3)),(Group(Admin, 2), User(Tom Waits, 3)))") {
    groupRepo.getGroupsByUserId(3).map {
      result => result shouldEqual Seq((Group("User", 1), User("Tom Waits", 3)),(Group("Admin", 2), User("Tom Waits", 3)))
    }
  }

  test("getAllGroups should return Seq(Group(User,1), Group(Admin, 2))") {
    groupRepo.getAllGroups.map {
      result => result shouldEqual Seq(Group("User",1), Group("Admin",2))
    }
  }

  test("getGroupById(1) should return None after deleteGroup(1)") {
    groupRepo.deleteGroup(1).flatMap {
      delRes => {
        delRes shouldEqual 1
        groupRepo.getGroupById(1).map {
          result => result shouldEqual None
        }
      }
    }
  }

  test("getAllGroups.length should return 3") {
    groupRepo.saveGroup(Group("Developer")).flatMap(
      addRes => {
        addRes shouldEqual 1
        groupRepo.getAllGroups.map {
          result => result.length shouldEqual 3
        }
      }
    )
  }
}