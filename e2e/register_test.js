const registerPage = require("./pages/registerPage");
const common = require("./pages/common");
const genuser = require("./helpers/genuser");

Feature('register');

Scenario('Login link redirect', (I) => {
    common.landOnPageSafely("/register", "Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("/login")
    I.see("Login");
});

const u1 = genuser();
Scenario('Both passwords dont match', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(u1.firstname, u1.lastname, `${u1.email}`, 'miss', 'match');
    I.seeElement('.error');
    I.see('Error: Password and password repeat must be the same');
});

const u2 = genuser();
Scenario('Created successfully', (I) => {
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(u2.firstname, u2.lastname, u2.email, u2.password, u2.password);
    I.dontSeeElement('.error');
    common.checkLoggedIn(u2.email);
    I.amOnPage("/questions");
});

Scenario('Email already used', (I) => {
    // Already used email address is john.doe@me.com and has been created in test above
    common.landOnPageSafely("/register", "Register");
    registerPage.fillAndRegisterUser(u2.firstname, u2.lastname, u2.email, u2.password, u2.password);
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});