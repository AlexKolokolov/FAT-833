package org.kolokolov.collections

/**
  * Created by andersen on 21.03.2017.
  */
object Collections {

  def zipToIndexes(collection: Seq[Any]) = {
    collection.zipWithIndex.map(t => t._2 -> t._1)
  }
}
