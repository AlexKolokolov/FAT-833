package org.kolokolov.slick.DBprofiles

import slick.jdbc.{H2Profile, JdbcProfile}

/**
  * Created by andersen on 04.04.2017.
  */
trait H2Database extends DatabaseProfile {
  override protected val profile: JdbcProfile = H2Profile
}
