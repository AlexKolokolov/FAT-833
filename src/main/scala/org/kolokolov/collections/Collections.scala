package org.kolokolov.collections

/**
  * Created by andersen on 21.03.2017.
  */
object Collections {

  def zipToIndexes(collection: Seq[Any]): Seq[(Int,Any)] = {
    collection.view.zipWithIndex.map(t => t._2 -> t._1)
  }

  def mapByLength(collection: List[String]): Map[Int,List[String]] = {
    collection.groupBy(_.length)
  }

  def sortAndSplitByLength(collection: Seq[String], length: Int): (Seq[String],Seq[String]) = {
    collection.sortWith(_.length < _.length).partition(_.length < length)
  }

  def flattenSeq(collection: Seq[Seq[Any]]): Seq[Any] = {
    collection.flatten
  }

  def countLetters(str: String): List[(Char,Int)] = {
    str.groupBy(ch => ch).mapValues(_.length).toList.sorted
  }

  def sortTupleList(tupleList: List[(Int,String)]): List[(Int,String)] = {
    tupleList.sortBy(_._2).reverse.sortBy(_._1)
  }
}
