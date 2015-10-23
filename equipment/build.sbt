lazy val root = (project in file(".")).settings(
  name := "equipment",
  organization := "com.sungevity",
  version := "0.0.1",
  scalaVersion := "2.11.7"
)

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-feature")

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

publishTo := Some("Artifactory Realm" at "https://sungevity.artifactoryonline.com/sungevity/libs-release-local")
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
libraryDependencies +=  "com.typesafe.play" %% "play-json" % "2.3.4"

// exclude play generated code from scoverage
ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;router\\..*"

// fail if coverage is less than 100%
ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 100
ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true
