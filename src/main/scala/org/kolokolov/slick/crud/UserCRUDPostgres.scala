package org.kolokolov.slick.crud

import slick.jdbc.{JdbcProfile, PostgresProfile}

/**
  * Created by andersen on 04.04.2017.
  */
trait UserCRUDPostgres extends UserCRUDModule {
  override protected val profile: JdbcProfile = PostgresProfile
}