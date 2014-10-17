name := """sample"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

JsEngineKeys.engineType := JsEngineKeys.EngineType.Node