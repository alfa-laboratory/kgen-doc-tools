let path = require('path');

let webpack = require('webpack');

let defined = {
    "PRODUCTION": true
};

// let UglifyJSPlugin = require(currentDir + node_directory + "uglifyjs-webpack-plugin");

var webpackConfig = {
    entry: 'js_parser',
    target: 'node',
    output: {
        path: path.resolve('./build/bundle'),
        publicPath: './build/',
        filename: 'main.bundle.js'
    },
    // resolve: {
    //     modules: [ path.resolve('js'), path.resolve('..', 'src'), path.resolve('.'), path.resolve('node_modules') ],
    //     extensions: ['.js', '.css', '.vue']
    // },
    resolve: {
        modules: [
            path.resolve("build/classes/kotlin/main"),
            path.resolve("src/main/resources"),
            path.resolve("node_modules"),
        ]
    },
    module: {
        rules: []
    },
    devtool: '#source-map',
    plugins: [
        new webpack.DefinePlugin(defined),
        // new UglifyJSPlugin()
    ]
};

webpackConfig.resolve.modules.unshift(path.resolve("./build/kotlin-js-min/main"), path.resolve("./build/kotlin-js-min/test"));

module.exports = webpackConfig;

