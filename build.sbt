import bintray.Keys._

sbtPlugin := true

organization := "com.github.dwickern"

name := "sbt-bower"

version := "1.0.2"

scalaVersion := "2.10.4"

addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "1.0.2")

resolvers ++= Seq(
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.url("sbt snapshot plugins", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("snapshots"),
  "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
  Resolver.mavenLocal
)

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

publishMavenStyle := false

bintraySettings

repository in bintray := "sbt-plugins"

bintrayOrganization in bintray := None

scriptedSettings

scriptedLaunchOpts <+= version apply { v => s"-Dproject.version=$v" }
