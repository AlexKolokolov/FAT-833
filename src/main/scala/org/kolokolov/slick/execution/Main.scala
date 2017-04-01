package org.kolokolov.slick.execution

import org.kolokolov.slick.domain.User
import org.kolokolov.slick.repo.UserRepo
import slick.jdbc.PostgresProfile
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kolokolov on 3/29/17.
  */
object Main extends App{

  val userRepo = new UserRepo(PostgresProfile)
  val dataBaseManager = new DataBaseManager(PostgresProfile)

  var done = false

//  dataBaseManager.setupDB.onComplete {
//    setupResult => {
//      userRepo.save(User("Bob Marley")).flatMap {
//        addResult => {
//          println("Users added: " + addResult)
//          userRepo.getAll.flatMap {
//            getResult => {
//              println("In database: " + getResult)
//              dataBaseManager.cleanDB.map {
//                cleanResult => {
//                  println("Database cleaned!")
//                  done = true
//                }
//              }
//            }
//          }
//        }
//      }
//    }
//  }

  for {
    setupResult <- dataBaseManager.setupDB
    addResult <- userRepo.save(User("Bob Marley"))
    getResult <- userRepo.getAll
    cleanResult <- dataBaseManager.cleanDB
  } yield {
    println("Users added: " + addResult)
    println("In database: " + getResult)
    println("Database cleaned!")
    done = true
  }

  while(!done) {
    Thread.sleep(100)
  }
}
