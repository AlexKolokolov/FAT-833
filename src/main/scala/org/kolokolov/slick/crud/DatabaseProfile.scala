package org.kolokolov.slick.crud

import org.kolokolov.slick.model.EntityHasId
import slick.jdbc.JdbcProfile

/**
  * Created by Alexey Kolokolov on 03.04.2017.
  */
trait DatabaseProfile {

  protected val profile: JdbcProfile

  import profile.api._

  trait TableHasId[E <: EntityHasId] extends Table[E] {
    def id: Rep[Int]
  }
}