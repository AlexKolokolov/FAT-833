package org.kolokolov.slick

import org.kolokolov.slick.domain.UserModule
import org.kolokolov.slick.execution.DataBaseManager
import org.kolokolov.slick.repo.UserRepo
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import slick.jdbc.{H2Profile, JdbcProfile}

/**
  * Created by kolokolov on 3/29/17.
  */
class UserRepoTest extends FunSuite
  with Matchers
  with BeforeAndAfter
  with UserModule {

  override val profile: JdbcProfile = H2Profile

  before {
    val dataBaseManager = new DataBaseManager(profile)
    dataBaseManager.setupDB
    Thread.sleep(2000)
  }

  test("getUserById(1) should return User(Bob Marley, 1)") {
    val userRepo = new UserRepo(profile)
    userRepo.getUserById(1) shouldEqual Some(User("Bob Marley",1))
  }
}
