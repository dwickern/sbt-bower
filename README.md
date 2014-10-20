# sbt-bower

An SBT plugin which downloads dependencies using [bower].

Compatible with Play 2.3.

## Installation

Add this line to your project's `plugins.sbt` file:

    addSbtPlugin("com.github.dwickern" % "sbt-bower" % "1.0.0")

Bower only runs in Node, so add this line to your `build.sbt` file:

    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

SBT doesn't support npm dependencies for plugins, so you have to include bower in your project. Create a `package.json` in the root of the project:

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


## License

Copyright 2014 Derek Wickern

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
