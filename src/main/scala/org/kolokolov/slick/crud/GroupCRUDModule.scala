package org.kolokolov.slick.crud

import org.kolokolov.slick.model.{Group, User}

import scala.concurrent.Future

/**
  * Created by andersen on 04.04.2017.
  */
trait GroupCRUDModule extends AbstractCRUDModule {

  import profile.api._

  class GroupTable(tag: Tag) extends Table[Group](tag, "group_table") with TableHasId[Group] {
    def id = column[Int]("group_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("group_name")
    def * = (name, id) <> (Group.tupled, Group.unapply)
  }

  protected lazy val groupTable = TableQuery[GroupTable]

  class GroupCRUD extends AbstractCRUD[Group, GroupTable] {
    override protected lazy val dataTable: TableQuery[GroupTable] = groupTable
  }
}
