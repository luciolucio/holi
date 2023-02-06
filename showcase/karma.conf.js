module.exports = function (config) {
    config.set({
        browsers: ['ChromeHeadless'],
        basePath: 'out',
        files: ["karma_tests.js"],
        frameworks: ['cljs-test'],
        plugins: ['karma-cljs-test', 'karma-chrome-launcher'],
        colors: true,
        logLevel: config.LOG_INFO,
        client: {
            args: ["shadow.test.karma.init"],
            singleRun: true
        },
        reporters: ['progress'],
        autoWatch: false,
        port: 9876,
    })
}
