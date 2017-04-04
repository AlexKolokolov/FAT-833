package org.kolokolov.slick.execution

import org.kolokolov.slick.crud.UserRepo
import org.kolokolov.slick.model.User
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
//    _ => {
//      userRepo.saveUser(User("Bob Marley")).flatMap {
//        addResult => {
//          println("Users added: " + addResult)
//          userRepo.getAllUsers.flatMap {
//            getResult => {
//              println("In database: " + getResult)
//              dataBaseManager.cleanDB.map {
//                _ => {
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
    _ <- dataBaseManager.setupDB
    addResult <- userRepo.saveUser(User("Bob Marley"))
    getResult <- userRepo.getAllUsers
    _ <- dataBaseManager.cleanDB
  } yield {
    println("Users added: " + addResult)
    println("Database content: " + getResult)
    println("Database is cleaned!")
    done = true
  }

  while(!done) {
    Thread.sleep(100)
  }
}