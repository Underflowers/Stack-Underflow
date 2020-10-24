const faker = require('faker');
const registerPage = require("./pages/registerPage");
const common = require("./pages/common");
const { fake } = require('faker');

Feature('register');

Scenario('Login link redirect', (I) => {
    common.landOnPageSafely("/register", "Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("/login")
    I.see("Login");
});

const firstname1 = faker.name.firstName();
const lastname1 = faker.name.lastName();

Scenario('Both passwords dont match', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstname1, lastname1, `${firstname1}.${lastname1}_${faker.random.number()}@stackunderflow.e2e`, 'miss', 'match');
    I.seeElement('.error');
    I.see('Error: Password and password repeat must be the same');
});

const firstname2 = faker.name.firstName();
const lastname2 = faker.name.lastName();
// Further test will try to use it once more
const emailAlreadyUse = `${firstname2}.${lastname2}_${faker.random.number()}@stackunderflow.e2e`;

Scenario('Created successfully', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(firstname2, lastname2, emailAlreadyUse, 'pwd1', 'pwd1');
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