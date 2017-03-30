//jshint strict: false
module.exports = function(config) {
  config.set({

    basePath: './webapp',

    files: [
      'bower_components/angular/angular.js',
      'bower_components/angular-route/angular-route.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'components/**/*.js',
      'unit_tests*/**/*.js'
    ],

    autoWatch: true,

    frameworks: ['jasmine'],

    //browsers: ['Chrome', 'PhantomJS'],
    browsers: ['Chrome'],

    plugins: [
      'karma-phantomjs-launcher',
      'karma-chrome-launcher',
      'karma-firefox-launcher',
      'karma-jasmine',
      'karma-junit-reporter'
    ],

    junitReporter: {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }

  });
};
