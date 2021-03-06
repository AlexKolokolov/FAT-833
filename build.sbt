name := "fat-833"

version := "1.1"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"  

//libraryDependencies += "org.specs2" % "specs2_2.9.1" % "1.12.4"

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.25",
														"ch.qos.logback" % "logback-classic" % "1.1.7")

libraryDependencies += "org.scala-lang.modules" % "scala-async_2.10" % "0.9.5"


