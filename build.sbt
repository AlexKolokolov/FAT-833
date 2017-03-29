name := "fat-833"

version := "1.2-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"  

//libraryDependencies += "org.specs2" % "specs2_2.9.1" % "1.12.4"

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.25",
														"ch.qos.logback" % "logback-classic" % "1.1.7")

libraryDependencies += "org.scala-lang.modules" % "scala-async_2.10" % "0.9.5"

libraryDependencies ++= Seq("com.typesafe.slick" %% "slick" % "3.2.0",
														"com.typesafe.slick" %% "slick-hikaricp" % "3.2.0")

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1212"

libraryDependencies += "com.h2database" % "h2" % "1.4.194"