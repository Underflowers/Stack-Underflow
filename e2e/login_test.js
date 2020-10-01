Feature('login');

const faker = require('faker');
const common = require("./pages/common");
const registerPage = require("./pages/registerPage");
const firstName = faker.name.firstName();
const lastname = faker.name.lastName();
const email = firstName + "." + lastname + "@me.com";
const pass = "test";

Scenario('Register link redirect', (I) => {
    common.landOnPageSafely("/login", "Login");
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    common.landOnPageSafely("/register", "Register");
});

Scenario('Created successfully for login', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstName, lastname, email, pass, pass);
    I.dontSeeElement('.error');
    I.see('Authenticated user: ' + firstName + ' ' + lastname);
});

Scenario('Login successfully', (I) => {
    common.landOnPageSafely("/login", "Login");
    I.fillField('email', email);
    I.fillField('password', pass);
    I.click('loginBtn');
    I.amOnPage('http://localhost:9080/StackUnderflow/questions');
});

Scenario('Login failed', (I) => {
    common.landOnPageSafely("/login", "Login");
    I.fillField('email', "hello@hello.com");
    I.fillField('password', "nopass");
    I.click('loginBtn');
    I.amOnPage('/login');
});