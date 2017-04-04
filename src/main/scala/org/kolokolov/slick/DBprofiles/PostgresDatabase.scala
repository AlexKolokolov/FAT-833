package org.kolokolov.slick.DBprofiles

import slick.jdbc.{JdbcProfile, PostgresProfile}

/**
  * Created by andersen on 04.04.2017.
  */
trait PostgresDatabase extends DatabaseProfile {
  override protected val profile: JdbcProfile = PostgresProfile
}
