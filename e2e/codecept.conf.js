exports.config = {
  tests: './*_test.js',
  output: './output',
  helpers: {
    Puppeteer: {
      url: 'http://localhost:9080',
      show: true,
      windowSize: '1200x900'
    }
  },
  include: {
    I: './steps_file.js',
    RegisterPage: './pages/register.page.js',
    LoginPage: './pages/login.page.js',
    AskPage: './pages/ask.page.js',
    QuestionsPage: './pages/questions.page.js',
    Navigation: './helpers/navigation.js'
  },
  bootstrap: null,
  mocha: {},
  name: 'e2e',
  plugins: {
    retryFailedStep: {
      enabled: true
    },
    screenshotOnFail: {
      enabled: true
    }
  }
}