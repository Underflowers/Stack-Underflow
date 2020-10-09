const faker = require('faker');
const registerPage = require("./pages/registerPage");
const common = require("./pages/common");

Feature('register');

Scenario('Login link redirect', (I) => {
    common.landOnPageSafely("/register", "Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("/login")
    I.see("Login");
});

Scenario('Both passwords dont match', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser('John', 'Doe', 'john.doe@me.com', 'john', 'doe');
    I.seeElement('.error');
    I.see('Error: Passwords don\'t match');
});

const firstname = faker.name.firstName();
const lastname = faker.name.lastName();
const emailAlreadyUse = firstname + '.' + lastname + '@me.com'; // Further test will try to use it once more
Scenario('Created successfully', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstname, lastname, emailAlreadyUse, 'pwd', 'pwd');
    I.dontSeeElement('.error');
    common.checkLoggedIn(emailAlreadyUse);
    I.amOnPage("/questions");
});

Scenario('Email already used', (I) => {
    // Already used email address is john.doe@me.com and has been created in test above
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser('test', 'test', emailAlreadyUse, 'test', 'test');
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});