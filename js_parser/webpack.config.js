let config = require('./build/WebPackHelper.js');
let path = require('path');

let currentDir = path.resolve(".");
let node_directory = '/node_modules/';
let webpack = require(currentDir + node_directory + 'webpack');

let defined = {
    "PRODUCTION": true
};

// let UglifyJSPlugin = require(currentDir + node_directory + "uglifyjs-webpack-plugin");

var webpackConfig = {
    entry: config.moduleName,
    target: 'node',
    output: {
        path: path.resolve('./bundle'),
        publicPath: '/build/',
        filename: 'main.bundle.js'
    },
    // resolve: {
    //     modules: [ path.resolve('js'), path.resolve('..', 'src'), path.resolve('.'), path.resolve('node_modules') ],
    //     extensions: ['.js', '.css', '.vue']
    // },
    resolve: {
        modules: [
            path.resolve("classes/kotlin/main"),
            path.resolve("../src/main/resources"),
            path.resolve("node_modules"),
            path.resolve("node_modules_imported")
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

webpackConfig.resolve.modules.unshift(path.resolve("./kotlin-js-min/main"), path.resolve("./kotlin-js-min/main"));

module.exports = webpackConfig;

