# sbt-bower

An SBT plugin which downloads dependencies using [bower].

## Installation

Add this line to your project's `plugins.sbt` file:

    addSbtPlugin("com.github.dwickern" % "sbt-bower" % "1.0.0")

Bower only runs in Node ([issue here](apigee/trireme#86)), so add this line to your `build.sbt` file:

    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

SBT doesn't support npm dependencies for plugins ([issue here](sbt/sbt-js-engine#6)), so you have to include bower in your project. Create a `package.json` in the root of the project:

    {
      "devDependencies": {
        "bower": "~1.3"
      }
    }

## Configuration

Create a [bower.json] file with your dependencies.

By default, dependencies are copied to `public/components`. To customize the output directory:

    target in bower := baseDirectory.value / "some" / "other" / "path"

[bower]: http://bower.io/
[bower.json]: http://bower.io/docs/creating-packages/
