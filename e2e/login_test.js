Feature('login');

const faker = require('faker');
const common = require("./pages/common");
const registerPage = require("./pages/registerPage");
const loginPage = require("./pages/loginPage");
const firstname = faker.name.firstName();
const lastname = faker.name.lastName();
const email = firstname + "." + lastname + "@me.com";
const pass = "test";

Scenario('Register link redirect', (I) => {
    common.landOnPageSafely("/login", "Login");
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    common.landOnPageSafely("/register", "Register");
});

Scenario('Created successfully for login', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstname, lastname, email, pass, pass);
    I.dontSeeElement('.error');
    common.checkLoggedIn(firstname, lastname);
});

Scenario('Login successfully', (I) => {
    common.landOnPageSafely("/login", "Login");
    loginPage.loginAs(email, pass);
    I.amOnPage('/questions');
});

Scenario('Login failed', (I) => {
    common.landOnPageSafely("/login", "Login");
    loginPage.loginAs("hello@hello.com", "nopass");
    I.amOnPage('/login');
});