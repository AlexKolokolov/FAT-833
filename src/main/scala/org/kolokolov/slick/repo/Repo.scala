package org.kolokolov.slick.repo

import org.kolokolov.slick.domain.UserGroupModule

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 31.03.2017.
  */
abstract class Repo[T] extends UserGroupModule {

  import profile.api._

  protected val dataTable: TableQuery[Table[T]]

  protected val db: Database = Database.forConfig("db.config")

  def save(data: T): Future[Int] = {
    db.run(dataTable += data)
  }

  def getAll: Future[Seq[T]] = {
    val all = dataTable.result
    db.run(all)
  }

  def getById(id: Int): Future[Option[T]] = {
    val byId = dataTable.filter {
      case data: Identifiable => data.id === id
    }.result.headOption
    db.run(byId)
  }

  def deleteById(id: Int): Future[Int] = {
    val deleteById = dataTable.filter {
      case data: Identifiable => data.id === id
    }.delete
    db.run(deleteById)
  }
}