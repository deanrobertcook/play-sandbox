import sbt.Keys._
import play.sbt.PlaySettings

val playMailerVersion = "8.0.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayService, PlayLayoutPlugin, Common)
  .settings(
    name := "play-scala-rest-api-example",
    scalaVersion := "2.13.6",
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
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )

lazy val gatlingVersion = "3.3.1"
lazy val gatling = (project in file("gatling"))
  .enablePlugins(GatlingPlugin)
  .settings(
    scalaVersion := "2.12.13",
    libraryDependencies ++= Seq(
      "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % Test,
      "io.gatling" % "gatling-test-framework" % gatlingVersion % Test
    )
  )

// Documentation for this project:
//    sbt "project docs" "~ paradox"
//    open docs/target/paradox/site/index.html
lazy val docs = (project in file("docs")).enablePlugins(ParadoxPlugin).
  settings(
    scalaVersion := "2.13.6",
    paradoxProperties += ("download_url" -> "https://example.lightbend.com/v1/download/play-samples-play-scala-rest-api-example")
  )
