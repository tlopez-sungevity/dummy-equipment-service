lazy val root = (project in file(".")).settings(
  name := "equipment-client",
  organization := "com.sungevity",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

scalacOptions ++= Seq("-Xlint", "-deprecation", "-feature")

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Artifactory Realm" at "https://sungevity.artifactoryonline.com/sungevity/libs-release-local"

publishTo := Some("Artifactory Realm" at "https://sungevity.artifactoryonline.com/sungevity/libs-release-local")
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "com.typesafe.play" %% "play-json" % "2.4.3",
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.play" %% "play-ws" % "2.4.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "com.sungevity" %% "play-siren" % "0.3.0",
  "com.sungevity" %% "equipment" % "0.1.0"
)

// fail if coverage is less than 100%
coverageMinimum := 100
coverageFailOnMinimum := true
coverageExcludedPackages := "service.equipment.client.EquipmentApiClientPlayModule"