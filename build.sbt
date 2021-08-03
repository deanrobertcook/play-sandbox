import sbt.Keys._
import play.sbt.PlaySettings

val playMailerVersion = "8.0.1"

val baseSettings = Seq(
  scalaVersion := Vsn.Scala,
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-encoding", "utf8", "-Ymacro-annotations", "-Xfatal-warnings"),
)

lazy val root = (project in file("."))
  .enablePlugins(PlayService, PlayLayoutPlugin, Common)
  .settings(baseSettings)
  .settings(
    name := "play-sandbox",
    libraryDependencies ++= Seq(
      guice,
      "org.joda" % "joda-convert" % "2.2.1",
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "1.5.1",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.typesafe.play" %% "play-mailer" % playMailerVersion,
      "com.typesafe.play" %% "play-mailer-guice" % playMailerVersion,

      //database
      jdbc,
      "mysql" % "mysql-connector-java" % "8.0.15",
      "org.playframework.anorm" %% "anorm" % "2.6.10"

    ),
  )
  .dependsOn(printMacro)

lazy val printMacro = Project("print-macro", file("modules/print"))
  .settings(baseSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % Vsn.Scala,
    )
  )