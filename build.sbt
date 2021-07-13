ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "org.drc"
ThisBuild / idePackagePrefix := Some("org.drc")

name := "play-docker"

version := "0.1"

val scalaTest = "org.scalatest" %% "scalatest" % "3.2.7"
val gigahorse = "com.eed3si9n" %% "gigahorse-okhttp" % "0.5.0"
val playJson  = "com.typesafe.play" %% "play-json" % "2.9.2"

lazy val hello = (project in file("."))
  .aggregate(helloCore)
  .dependsOn(helloCore)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "Hello",
    libraryDependencies += scalaTest % Test
  )

lazy val helloCore = (project in file("hcore"))
  .settings(
    name := "Hello Core",
    libraryDependencies ++= Seq(gigahorse, playJson),
    libraryDependencies += scalaTest % Test,
  )

//lazy val core: Project = (project in file("hcore"))
//  .enablePlugins(DockerPlugin, JavaServerAppPackaging)
//  .settings(
//    packageName in Docker := "docker-test"
//  )