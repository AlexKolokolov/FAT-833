package org.kolokolov.slick.execution

import org.kolokolov.slick.crud.PostgresDatabase
import org.kolokolov.slick.model.User
import org.kolokolov.slick.service.UserService
import slick.jdbc.PostgresProfile

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kolokolov on 3/29/17.
  */
object Main extends App{

  val userService = new UserService with PostgresDatabase
  val dataBaseManager = new DataBaseManager(PostgresProfile)

  var done = false

//  dataBaseManager.setupDB.onComplete {
//    _ => {
//      userService.saveUser(User("Bob Marley")).flatMap {
//        addResult => {
//          println("Users added: " + addResult)
//          userService.getAllUsers.flatMap {
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
    addResult <- userService.saveUser(User("Bob Marley"))
    getResult <- userService.getAllUsers
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