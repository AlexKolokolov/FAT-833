val scalatraVersion = "2.5.0"

lazy val root = (project in file(".")).settings(
	name := "fat-833",
	version := "1.2-SNAPSHOT",
	scalaVersion := "2.11.8",
	parallelExecution in Test := false,
	fork in run := true,
	libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
	//libraryDependencies += "org.specs2" % "specs2_2.9.1" % "1.12.4"
	libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.25",
		"ch.qos.logback" % "logback-classic" % "1.1.7"),
	libraryDependencies += "org.scala-lang.modules" % "scala-async_2.10" % "0.9.5",
	libraryDependencies ++= Seq("com.typesafe.slick" %% "slick" % "3.2.0",
		"com.typesafe.slick" %% "slick-hikaricp" % "3.2.0"),
	libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1212",
	libraryDependencies += "com.h2database" % "h2" % "1.4.194",
	resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases",
	scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
	libraryDependencies ++= Seq(
		"org.scalatra" %% "scalatra" % scalatraVersion,
		"org.scalatra" %% "scalatra-json" % scalatraVersion,
		"org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",
		"org.json4s" %% "json4s-jackson" % "3.5.1",
		"javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"),
	libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0",
	libraryDependencies ++= Seq(
		"com.typesafe.akka" %% "akka-actor" % "2.4.16",
		"net.databinder.dispatch" %% "dispatch-core" % "0.12.0")
).settings(jetty(): _*)