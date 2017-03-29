package org.kolokolov.slick.domain

import slick.jdbc.JdbcProfile

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
trait DataBaseProfile {
  val profile: JdbcProfile
}
