package org.kolokolov.slick.execution

import org.kolokolov.slick.DBprofiles.PostgresDatabase
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.UserService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by andersen on 03.04.2017.
  */
object DBCreator extends App {
  val dbManager = new DataBaseManager with PostgresDatabase
  val userService = new UserService with PostgresDatabase

  var done = false

  for {
    _ <- dbManager.setupDB
    _ <- userService.saveUser(User("Bob Marley"))
    _ <- userService.saveUser(User("Elvis Presley"))
    - <- userService.saveUser(User("Tom Waits"))
  } yield {
    println("Done!")
    done = true
  }

  while(!done) {
    Thread.sleep(100)
  }
}
