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

Scenario('Created successfully', (I) => {
    const firstname = faker.name.firstName();
    const lastname = faker.name.lastName();
    const email = firstname + "." + lastname + '@me.com';

    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstname, lastname, email, 'pwd', 'pwd');
    I.dontSeeElement('.error');
    common.checkLoggedIn(firstname, lastname);
    I.amOnPage("/questions");
});

Scenario('Email already used', (I) => {
    I.amOnPage("/register");
    I.see("Register");
    I.dontSeeElement('.error');
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser('test', 'test', 'test@email.com', 'test', 'test');
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});