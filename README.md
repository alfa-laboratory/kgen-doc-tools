# kgen-doc-tools

Project contains utils to generate `html` documentation from `markdown` text with custom extensions (like include, title etc.)

* __common__ - contains parser nodes and layout
* __files__ - contains input files, example layout and output directory contains `css` and `js` static files
* __jvm_html_render__ - header preprocessor which collect header nodes from parser and render it to `html` text by passing layout
* __jvm_server__ - simple server to demonstrate possibilities with live parser reloading and editing text in `web`
* __parser__ - custom markdown parser with extensions like include, title etc.

For demonstration run `main` method from `jvm_server/ru.alfabank.ecomm.dcreator.server.Application.kt` and open `http://localhost:8080/index.html`

 