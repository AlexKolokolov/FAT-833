package org.kolokolov.slick.crud

import slick.jdbc.PostgresProfile

/**
  * Created by andersen on 04.04.2017.
  */
trait GroupCRUDPostgres extends GroupCRUDModule {
  override protected val profile = PostgresProfile
}
