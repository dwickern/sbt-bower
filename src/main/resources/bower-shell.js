/*global process, require */

'use strict';

var args = process.argv,
    cwd = args[2],
    target = args[3];

process.chdir(cwd);

var bower = require('bower');

bower.commands.install(null, null, { cwd: cwd, directory: target })
    .on('error', function(error) {
        console.log("\u0010" + JSON.stringify({ success: false, error: error.message }));
    })
    .on('end', function() {
        console.log("\u0010" + JSON.stringify({ success: true }));
    });
