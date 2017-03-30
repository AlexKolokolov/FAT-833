package org.kolokolov.slick

import org.kolokolov.slick.execution.DataBaseManager
import org.kolokolov.slick.repo.UserRepo
import org.scalatest.{AsyncFunSuite, BeforeAndAfter, Matchers}
import slick.jdbc.H2Profile

/**
  * Created by kolokolov on 3/29/17.
  */
class UserRepoTest extends AsyncFunSuite
  with Matchers
  with BeforeAndAfter {

  before {
    val dataBaseManager = new DataBaseManager(H2Profile)
    dataBaseManager.setupDB
    Thread.sleep(2000)
  }

  test("getUserById(1) should return User(Bob Marley, 1)") {
    val userRepo = new UserRepo(H2Profile)
    userRepo.getUserById(1) map {
      result => result shouldEqual Some(userRepo.User("Bob Marley",1))
    }
  }

  test("getUserByGroupId(2) should return Seq((User(Tom Waits, 3), Group(Admin, 2)))") {
    val userRepo = new UserRepo(H2Profile)
    userRepo.getUsersByGroupId(2) map {
      result => result shouldEqual Seq((userRepo.User("Tom Waits",3), userRepo.Group("Admin", 2)))
    }
  }

  test("getAllUsers should return Seq(User(Bob Marley,1), User(Ron Perlman, 2), User(Tom Waits, 3))") {
    val userRepo = new UserRepo(H2Profile)
    userRepo.getAllUsers map {
      result => result shouldEqual Seq(userRepo.User("Bob Marley", 1), userRepo.User("Ron Perlman", 2), userRepo.User("Tom Waits", 3))
    }
  }
}
