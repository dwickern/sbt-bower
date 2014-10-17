package com.github.dwickern

import com.typesafe.sbt.jse.{SbtJsEngine, SbtJsTask}
import com.typesafe.sbt.web.SbtWeb
import sbt.Keys._
import sbt._
import spray.json._

import scala.concurrent.duration._

object Import {
  val bower = TaskKey[Seq[File]]("bower", "Install bower dependencies")

  object BowerKeys {
    val bowerTimeout = SettingKey[FiniteDuration]("bower-timeout", "The maximum number amount of time for bower to do its thing.")
  }
}

object SbtBower extends AutoPlugin {

  override def requires = SbtJsTask
  override def trigger = AllRequirements

  val autoImport = Import

  import autoImport._
  import BowerKeys._
  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import SbtJsEngine.autoImport.JsEngineKeys._

  val settings = Seq(
    bowerTimeout := 5.minutes,

    // SBT can't load NPM modules from the Plugin,
    // so the user of this plugin must provide bower
    bower := runBower.dependsOn(nodeModules in Assets).value,

    // Generate as sources instead of resources because they
    // might be used in the asset pipeline (e.g. uglify)
    sourceGenerators in Assets <+= bower
  )

  val jsTaskSettings = inTask(bower) {
    SbtJsTask.jsTaskSpecificUnscopedSettings ++ Seq(
      moduleName := "bower",
      target := (resourceDirectory in Assets).value / "components",
      shellFile := getClass.getClassLoader.getResource("bower-shell.js")
    )
  }

  override def projectSettings = settings ++ jsTaskSettings

  private def runBower: Def.Initialize[Task[Seq[File]]] = Def.task {
    val logger = streams.value.log("bower")

    val config = baseDirectory.value / "bower.json"

    if (config.exists()) {
      logger.info(s"Downloading Bower dependencies for ${projectInfo.value.nameFormal}")

      val modules = (nodeModuleDirectories in Assets).value.map(_.getPath)

      // in the bower-shell script we will need to change the working directory
      // from /project/target/bower/ to the directory for the current project
      val cwd = baseDirectory.value.toPath

      // bower wants the output directory relative to the base directory
      val output = (target in bower).value
      val relative = cwd.relativize(output.toPath)
      val args = Seq(cwd.toString, relative.toString)

      val results = SbtJsTask.executeJs(
        state.value,
        (engineType in bower).value,
        (command in bower).value,
        modules,
        (shellSource in bower).value,
        args,
        bowerTimeout.value
      )

      val error = for {
        JsObject(fields) <- results.headOption
        JsBoolean(success) <- fields.get("success") if !success
        JsString(error) <- fields.get("error")
      } yield error

      error.foreach(logger.error(_))

      output.***.get.filterNot(_.isDirectory)
    } else {
      logger.info(s"No bower.json present for ${projectInfo.value.nameFormal}")
      Seq()
    }
  }
}