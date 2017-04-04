package org.kolokolov.slick.crud

import slick.jdbc.{H2Profile, JdbcProfile}

/**
  * Created by andersen on 04.04.2017.
  */
trait GroupCRUDH2 extends GroupCRUDModule {
  override protected val profile: JdbcProfile = H2Profile
}
