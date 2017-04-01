package org.kolokolov.slick

import org.kolokolov.slick.domain.{Group, User}
import org.kolokolov.slick.repo.UserRepo
import org.scalatest.{AsyncFunSuite, BeforeAndAfterEach, Matchers}
import slick.jdbc.H2Profile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kolokolov on 3/29/17.
  */
class UserRepoTest extends AsyncFunSuite
  with Matchers
  with BeforeAndAfterEach {

  private val testDataBaseManager = new TestDataBaseManager(H2Profile)
  private val userRepo = new UserRepo(H2Profile)

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("getUserById(1) should return User(Bob Marley, 1)") {
    userRepo.getById(1).map {
      result => result shouldEqual Some(User("Bob Marley",1))
    }
  }

  test("getUsersByGroupId(2) should return Seq((User(Tom Waits, 3), Group(Admin, 2)))") {
    userRepo.getUsersByGroupId(2).map {
      result => result shouldEqual Seq((User("Tom Waits",3), Group("Admin", 2)))
    }
  }

  test("getAllUsers should return Seq(User(Bob Marley,1), User(Ron Perlman, 2), User(Tom Waits, 3))") {
    userRepo.getAll.map {
      result => result shouldEqual Seq(User("Bob Marley", 1), User("Ron Perlman", 2), User("Tom Waits", 3))
    }
  }

  test("getUserById(1) should return None after deleteUser(1)") {
    userRepo.deleteById(1).flatMap {
      delRes => {
        delRes shouldEqual 1
        userRepo.getById(1).map {
          result => result shouldEqual None
        }
      }
    }
  }

  test("getUserByGroupId(2) should return " +
    "Seq((User(Tom Waits, 3), Group(Admin, 2)),(User(Bob Marley, 1), Group(Admin, 2))) " +
    "after addUserToGroup(1,2)") {
    userRepo.addUserToGroup(1,2).flatMap {
      addRes => {
        addRes shouldEqual 1
        userRepo.getUsersByGroupId(2).map {
          result => result shouldEqual Seq((User("Tom Waits", 3), Group("Admin", 2)),
            (User("Bob Marley", 1), Group("Admin", 2)))
        }
      }
    }
  }

  test("getAllUsers.length should return 4") {
    userRepo.save(User("Johnny Cash")).flatMap(
      addRes => {
        addRes shouldEqual 1
        userRepo.getAll.map {
          result => result.length shouldEqual 4
        }
      }
    )
  }
}