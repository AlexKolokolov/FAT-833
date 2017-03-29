package org.kolokolov.slick

import org.kolokolov.slick.domain.UserModule
import org.kolokolov.slick.execution.DataBaseManager
import org.kolokolov.slick.repo.UserRepo
import org.scalatest.FunSuite
import slick.jdbc.{H2Profile, JdbcProfile}

/**
  * Created by kolokolov on 3/29/17.
  */
class UserRepoTest extends FunSuite with UserModule {

  override val profile: JdbcProfile = H2Profile

  val dataBaseManager = new DataBaseManager(profile)
  dataBaseManager.setupDB

  val userRepo = new UserRepo(profile)

  test("getUserById(1) should return User(Bob Marley, 1)") {
    val expectedResult = User("Bob Marley", 1)
    assert(userRepo.getUserById(1) == expectedResult)
  }

}
