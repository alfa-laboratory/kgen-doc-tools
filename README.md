# kgen-doc-tools

[![travis build](https://travis-ci.org/alfa-laboratory/kgen-doc-tools.svg?branch=master)](https://travis-ci.org/alfa-laboratory/kgen-doc-tools)
<a href="http://slack.kotlinlang.org/"><img src="http://slack.kotlinlang.org/badge.svg" height="20"></a>
[![codecov](https://codecov.io/gh/alfa-laboratory/kgen-doc-tools/branch/master/graph/badge.svg)](https://codecov.io/gh/alfa-laboratory/kgen-doc-tools)
[![MIT Licence](https://img.shields.io/badge/licence-MIT-blue.svg)](https://github.com/alfa-laboratory/kgen-doc-tools/blob/master/LICENSE)

## Project contains utils to generate `html` documentation from `markdown` text with custom extensions (like include, title etc.)

### Structure:

* __common_nodes__ - contains parser nodes 
* __common_parser__ - contains common markdown parser implementation with custom extensions like include, title etc
* __files__ - contains input files, example layout and output directory contains `css` and `js` static files
* __jvm_html_render__ - collect post processors and render implementation to render parsed nodes to `html` using layout from input folder
* __jvm_server__ - simple server to demonstrate possibilities with live parser reloading and editing text in `web`
* __jvm_parser__ - JVM realization of __common_parser__
* __js_parser__ - JS realization of __common_parser__ (with translated TypeScript declaration of NodeJs to Kotlin)

### Build requirements:
- installed `java 8`
- installed `node 10`

### Build:

Build project:
```sh
./gradlew buildWithResources
```
And run after with next command:
```sh
java -jar jvm_server/build/libs/jvm_server-all.jar <path_to_files>
```
Where `<path_to_files>` is path to files directory with layout. If directory is empty, then it will be created with needed structure.

### Development run:
- run client side with `webpack` in dev mode: 
```sh
cd jvm_server/public 
npm i 
npm run devNoServer
```
- run jvm_server in test mode:
```
./gradlew buildWithResources
cd jvm_server
java -jar build/libs/jvm_server-all.jar <path_to_files> test
```
- open page `http://localhost:8080`
