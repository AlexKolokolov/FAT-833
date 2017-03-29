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

//  val userRepo = new UserRepo(H2Profile)

}
