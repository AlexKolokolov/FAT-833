package org.kolokolov.collections

/**
  * Created by andersen on 21.03.2017.
  */
object Collections {

  def zipToIndexes(collection: Seq[Any]): Seq[(Int,Any)] = {
    collection.view.zipWithIndex.map(t => t._2 -> t._1).force
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
    implicit object TupleOrdering extends Ordering[(Int,String)] {
      override def compare(a: (Int,String), b: (Int,String)): Int = {
        if (a._1 == b._1) -a._2.compareTo(b._2)
        else a._1.compareTo(b._1)
      }
    }
    tupleList.sorted
  }

  def sumCollectionsElements(c1: Seq[Int], c2: Seq[Int]): Seq[Int] = {
    c1.zip(c2).map(t => t._1 + t._2)
  }
}
