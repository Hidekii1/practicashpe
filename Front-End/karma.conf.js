// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html
// Si usas Puppeteer, informa a Karma de la ruta de Chrome
// y hazlo **antes** de module.exports
try {
  process.env.CHROME_BIN = require('puppeteer').executablePath();
} catch (e) {
  // si no usas Puppeteer, asumimos que hay un chrome/chromium en PATH
}

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
	
    // Le decimos que levante este navegador
    browsers: ['ChromeHeadlessCI'],

    // Definimos el launcher CI
    customLaunchers: {
      ChromeHeadlessCI: {
        base: 'ChromeHeadless',
        flags: [
          '--no-sandbox',        // necesario en muchos entornos Linux sin privilegios
          '--disable-gpu',       // evita errores en entornos sin GPU
          '--disable-extensions',
          '--disable-dev-shm-usage',
          '--headless',
          '--remote-debugging-port=9222'
        ]
      }
    },

    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    client: {
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    coverageIstanbulReporter: {
      dir: require('path').join(__dirname, './coverage/its-app-frontend'),
      // genera HTML y lcov.info
      reports: ['html', 'lcovonly', 'text-summary'],
      fixWebpackSourcePaths: true
    },
    reporters: ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false,
    restartOnFileChange: true
  });
};
