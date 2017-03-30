package org.kolokolov.slick.execution

import org.kolokolov.slick.repo.UserRepo
import slick.jdbc.{H2Profile, PostgresProfile}

/**
  * Created by kolokolov on 3/29/17.
  */
object Main extends App{

  val dataBaseManager = new DataBaseManager(PostgresProfile)
  dataBaseManager.setupDB

  Thread.sleep(2000)

  val userRepo = new UserRepo(PostgresProfile)
  println(userRepo.getAllUsers)

  Thread.sleep(2000)

  dataBaseManager.cleanDB
}
