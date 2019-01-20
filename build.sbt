import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "in.varadharajan"
ThisBuild / organizationName := "in.varadharajan"

lazy val root = (project in file("."))
  .settings(
    name := "stm_demo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += ("org.scala-stm" %% "scala-stm" % "0.9")
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
