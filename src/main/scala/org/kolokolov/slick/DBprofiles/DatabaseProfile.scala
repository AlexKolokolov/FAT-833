package org.kolokolov.slick.DBprofiles

import slick.jdbc.JdbcProfile

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
trait DatabaseProfile {

  protected val profile: JdbcProfile

  import profile.api._

  protected val dataBase: Database = Database.forConfig("db.config")
}