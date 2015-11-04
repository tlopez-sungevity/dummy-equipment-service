name := """equipment-service"""

version := "0.1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatest" %% "scalatest" % "2.2.1" % "it,test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "it,test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "it,test",
  "com.typesafe.play" %% "play-slick" % "1.0.0",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "com.sungevity" %% "equipment" % "0.1.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Artifactory Realm" at "https://sungevity.artifactoryonline.com/sungevity/libs-release-local"
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

lazy val testAll = TaskKey[Unit]("test-all")

lazy val itSettings =
  inConfig(IntegrationTest extend Runtime)(Defaults.testSettings) ++
    Seq(
      fork in IntegrationTest := false,
      parallelExecution in IntegrationTest := false,
      scalaSource in IntegrationTest := baseDirectory.value / "integration-test",
      resourceDirectories in IntegrationTest := Seq(baseDirectory.value / "integration-test/resources"))

lazy val settings = itSettings ++ Seq(
  testAll <<= (test in IntegrationTest).dependsOn(test in Test)
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(settings: _*)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

scalacOptions ++= Seq("-Xlint", "-deprecation", "-feature")

libraryDependencies += "org.scoverage" %% "scalac-scoverage-runtime" % "1.1.1"

// exclude play generated code from scoverage
coverageExcludedPackages := "<empty>;Reverse.*;router\\..*;service.equipment.slick.EquipmentModule"

// fail if coverage is less than 100%
coverageMinimum := 100
coverageFailOnMinimum := true
