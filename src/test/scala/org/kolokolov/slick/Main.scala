package org.kolokolov.slick

import org.kolokolov.slick.execution.DataBaseManager
import org.kolokolov.slick.repo.UserRepo
import slick.jdbc.H2Profile

/**
  * Created by kolokolov on 3/29/17.
  */
object Main extends App {

  val dataBaseManager = new DataBaseManager(H2Profile)
  dataBaseManager.setupDB

  Thread.sleep(5000)

  val userRepo = new UserRepo(H2Profile)
  println(userRepo.getAllUsers)
  println(userRepo.getUsersByGroupId(1))

  Thread.sleep(5000)
}
