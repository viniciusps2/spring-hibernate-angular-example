var rootPath = __dirname + '/';

module.exports = function (config) {
    config.set({
        basePath: '',
        port: 9876,
        frameworks: ['wiredep', 'jasmine'],
        colors: true,
        logLevel: config.LOG_INFO,
        files: ["../public/app.js",
            "../public/routes.js",
            "../public/account/account-controller.js",
            "../public/account/account-edit-controller.js",
            "../public/account/account-service.js",
            "../public/atm/atm-controller.js",
            "../public/atm/atm-edit-controller.js",
            "../public/atm/atm-service.js",
            "../public/atm/withdraw-controller.js",
            "../public/components/focus-directive.js",
            "../public/components/util.js",
            "../public/home/home-controller.js",
            "../public/sortition/sortition-controller.js",
            "../public/sortition/sortition-modal-controller.js",
            "../public/sortition/sortition-service.js",
            "../public/components/confirm-dialog/confirm-dialog.js",
            "./**/*spec.js"],
        browsers: ['PhantomJS'],
        reporters: ['spec'],
        autoWatch: true,
        singleRun: false,
        wiredep: {
            dependencies: true,
            cwd: '..'
        },
        plugins : ['karma-wiredep', 'karma-jasmine', 'karma-phantomjs-launcher', 'karma-spec-reporter']
    });
};