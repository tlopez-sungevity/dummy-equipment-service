lazy val root = (project in file(".")).settings(
  name := "equipment",
  organization := "com.sungevity",
  version := "0.2.0",
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
  "com.typesafe.play" %% "play-json" % "2.3.10",
  "org.joda" % "joda-convert" % "1.6",
  "com.sungevity" %% "play-siren" % "0.3.0"
)

// fail if coverage is less than 100%
coverageMinimum := 100
coverageFailOnMinimum := true
