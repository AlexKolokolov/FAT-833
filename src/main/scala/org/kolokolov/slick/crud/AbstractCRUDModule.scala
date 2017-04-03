package org.kolokolov.slick.crud

import org.kolokolov.slick.model.EntityHasId

import scala.concurrent.Future

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
trait AbstractCRUDModule extends DatabaseProfile {

  abstract class AbstractCRUD[E <: EntityHasId, T <: TableHasId[E]] {

    import profile.api._

    protected val dataBase: Database = Database.forConfig("db.config")

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
