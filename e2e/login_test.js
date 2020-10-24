Feature('login');

const common = require("./pages/common");
const registerPage = require("./pages/registerPage");
const loginPage = require("./pages/loginPage");
const genuser = require("./helpers/genuser");

const u = genuser();
const pass = "test";

Scenario('Register link redirect', (I) => {
    common.landOnPageSafely("/login", "Login");
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    common.landOnPageSafely("/register", "Register");
});

Scenario('Created successfully for login', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(u.firstname, u.lastname, u.email, u.password, u.password);
    I.dontSeeElement('.error');
    common.checkLoggedIn(u.email);
});

Scenario('Login successfully', (I) => {
    common.landOnPageSafely("/login", "Login");
    loginPage.loginAs(u.email, u.password);
    I.amOnPage('/questions');
});

Scenario('Login failed', (I) => {
    common.landOnPageSafely("/login", "Login");
    loginPage.loginAs("hello@@stackunderflow.e2e", "nopass");
    I.amOnPage('/login');
});