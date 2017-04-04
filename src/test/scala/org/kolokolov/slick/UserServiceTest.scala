package org.kolokolov.slick

import org.kolokolov.slick.crud.UserCRUDH2
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.UserService
import org.scalatest.{AsyncFunSuite, BeforeAndAfterEach, Matchers}
import slick.jdbc.H2Profile

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kolokolov on 3/29/17.
  */
class UserServiceTest extends AsyncFunSuite
  with Matchers
  with BeforeAndAfterEach {

  private val userService = new UserService with UserCRUDH2

  private val testDataBaseManager = new TestDataBaseManager(H2Profile)

  override def beforeEach: Unit = {
    Await.result(testDataBaseManager.setupDB, Duration(10, "sec"))
  }

  override def afterEach: Unit = {
    Await.result(testDataBaseManager.cleanDB, Duration(10, "sec"))
  }

  test("getUserById(1) should return User(Bob Marley, 1)") {
    userService.getUserById(1).map {
      result => result shouldEqual Some(User("Bob Marley",1))
    }
  }

  test("getAllUsers should return Seq(User(Bob Marley,1), User(Ron Perlman, 2), User(Tom Waits, 3))") {
    userService.getAllUsers.map {
      result => result shouldEqual Seq(User("Bob Marley", 1), User("Ron Perlman", 2), User("Tom Waits", 3))
    }
  }

  test("getUserById(1) should return None after deleteUser(1)") {
    userService.deleteUser(1).flatMap {
      delRes => {
        delRes shouldEqual 1
        userService.getUserById(1).map {
          result => result shouldEqual None
        }
      }
    }
  }

  test("getAllUsers.length should return 4") {
    userService.saveUser(User("Johnny Cash")).flatMap(
      addRes => {
        addRes shouldEqual 1
        userService.getAllUsers.map {
          result => result.length shouldEqual 4
        }
      }
    )
  }
}