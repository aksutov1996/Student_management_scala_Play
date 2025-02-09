import play.sbt.PlayImport.{guice, ws}

name := """StudentManagement"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "org.postgresql" % "postgresql" % "42.2.18", // Драйвер PostgreSQL
  "com.typesafe.akka" %% "akka-actor" % "2.6.14", // Для работы с актором в Play
  "com.typesafe.play" %% "play-json" % "2.9.2", // Для json
  "org.playframework" %% "play-slick" % "6.1.1", //slick c github.com/playframework/play-samples
  "org.playframework" %% "play-slick-evolutions" % "6.1.1", //slick c github.com/playframework/play-samples
  ws //oauth2
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
