package org.kolokolov.slick.domain

import slick.jdbc.JdbcProfile

/**
  * Created by Alexey Kolokolov on 29.03.2017.
  */
trait DataBaseProfile {
  protected val profile: JdbcProfile

  protected trait Identifiable {
    def id: profile.api.Rep[Int]
  }
}