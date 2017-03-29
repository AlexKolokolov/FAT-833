package org.kolokolov.slick.domain

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
trait GroupModule extends DataBaseProfile {

  import profile.api._

  class GroupTable(tag: Tag) extends Table[Group](tag, "group_table") {
    def name = column[String]("group_name")
    def id = column[Int]("group_id", O.PrimaryKey, O.AutoInc)
    def * = (name, id) <> (Group.tupled, Group.unapply)
  }

  case class Group(name: String, id: Int = 0)

  lazy val groupTable = TableQuery[GroupTable]
}



