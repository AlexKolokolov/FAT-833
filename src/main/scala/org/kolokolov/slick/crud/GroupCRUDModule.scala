package org.kolokolov.slick.crud

import org.kolokolov.slick.DBprofiles.DatabaseProfile
import org.kolokolov.slick.model.Group

/**
  * Created by Alexey Kolokolov on 04.04.2017.
  */
trait GroupCRUDModule extends AbstractCRUDModule {

  self: DatabaseProfile =>

  import profile.api._

  class GroupTable(tag: Tag) extends Table[Group](tag, "group_table") with TableHasId[Group] {
    def id = column[Int]("group_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("group_name")
    def * = (name, id) <> (Group.tupled, Group.unapply)
  }

  object GroupCRUD extends AbstractCRUD[Group, GroupTable] {
    override lazy val dataTable: TableQuery[GroupTable] = TableQuery[GroupTable]
  }
}
