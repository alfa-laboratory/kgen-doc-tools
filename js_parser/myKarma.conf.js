var webpackConfig = require('../webpack.config.js');
// webpackConfig.entry = {};

module.exports = function (config) {
    config.set({
        basePath: '',
        frameworks: ['mocha'],

        reporters: ['mocha'],
        port: 9876,
        colors: false,
        logLevel: config.LOG_INFO,
        autoWatch: true,
        // browsers: ['Chrome'],

        captureTimeout: 5000,
        singleRun: true,
        reportSlowerThan: 500,

        files: [
            './classes/kotlin/test/*.js'
        ],

        preprocessors: {
            './classes/kotlin/test/*.js': ['webpack'],
        },

        browsers: [
            'ChromeHeadlessNoSandbox'
        ],
        customLaunchers: {
            ChromeHeadlessNoSandbox: {
                base: 'ChromeHeadless',
                flags: ['--no-sandbox']
            }
        },

        webpack: webpackConfig,

        webpackMiddleware: {
            noInfo: true
        }
    })
};