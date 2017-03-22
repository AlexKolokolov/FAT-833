package org.kolokolov.collections

import scala.collection.immutable.TreeMap

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
    def addLetterToMap(letter: Char, map: TreeMap[Char,Int]): TreeMap[Char,Int] = {
      map + (letter -> (map.getOrElse(letter, 0) + 1))
    }
    str.foldLeft(new TreeMap[Char,Int])((map, ch) => addLetterToMap(ch, map)).toList
  }

  def sortTupleList(tupleList: List[(Int,String)]): List[(Int,String)] = {
    tupleList.view.map(_._1).sorted.toList zip tupleList.view.map(_._2).sorted.reverse.toList
  }
}
