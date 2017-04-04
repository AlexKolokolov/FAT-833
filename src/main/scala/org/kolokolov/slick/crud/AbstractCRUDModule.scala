package org.kolokolov.slick.crud

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.EntityHasId

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
trait AbstractCRUDModule {

  self: DatabaseProfile =>

  import profile.api._

  trait TableHasId[E <: EntityHasId] extends Table[E] {
    def id: Rep[Int]
  }

  abstract class AbstractCRUD[E <: EntityHasId, T <: TableHasId[E]] {

    protected val dataTable: TableQuery[T]

    def save(entity: E): Future[Int] = {
      val saveAction = dataTable += entity
      dataBase.run(saveAction)
    }

    def getAll: Future[Seq[E]] = {
      val getAllAction = dataTable.result
      dataBase.run(getAllAction)
    }

    def getById(id: Int): Future[Option[E]] = {
      val getByIdAction = dataTable.filter(_.id === id).result.headOption
      dataBase.run(getByIdAction)
    }

    def deleteById(id: Int): Future[Int] = {
      val deleteByIdAction = dataTable.filter(_.id === id).delete
      dataBase.run(deleteByIdAction)
    }
  }
}
