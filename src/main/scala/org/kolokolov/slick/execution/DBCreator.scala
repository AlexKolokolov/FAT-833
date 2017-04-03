package org.kolokolov.slick.execution

import org.kolokolov.slick.domain.User
import org.kolokolov.slick.repo.UserRepo
import slick.jdbc.PostgresProfile
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by andersen on 03.04.2017.
  */
object DBCreator extends App {
  val dbManager = new DataBaseManager(PostgresProfile)
  val userRepo = new UserRepo(PostgresProfile)

  var done = false

  for {
    _ <- dbManager.setupDB
    _ <- userRepo.save(User("Bob Marley"))
    _ <- userRepo.save(User("Elvis Presley"))
    - <- userRepo.save(User("Tom Waits"))
  } yield {
    println("Done!")
    done = true
  }

  while(!done) {
    Thread.sleep(100)
  }
}
