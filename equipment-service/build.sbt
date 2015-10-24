name := """equipment-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test",
  "com.typesafe.play" %% "play-slick" % "1.0.0",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "com.sungevity" %% "equipment" % "0.0.1-SNAPSHOT"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Artifactory Realm" at "https://sungevity.artifactoryonline.com/sungevity/libs-release-local"
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

scalacOptions ++= Seq("-Xlint", "-deprecation", "-feature")

// exclude play generated code from scoverage
ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;router\\..*"

// fail if coverage is less than 100%
ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 100
ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true
