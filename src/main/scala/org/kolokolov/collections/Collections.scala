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
    val map = new scala.collection.mutable.TreeMap[Char,Int].withDefaultValue(0)
    str.foreach(ch => map += (ch -> (map(ch) + 1)))
    map.toList
  }

  def sortTupleList(tupleList: List[(Int,String)]): List[(Int,String)] = {
    tupleList.view.map(_._1).sorted.toList zip tupleList.view.map(_._2).sorted.reverse.toList
  }
}
