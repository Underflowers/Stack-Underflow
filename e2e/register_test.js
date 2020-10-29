const genuser = require("./helpers/genuser");

Feature('register');

Scenario('Login link redirect', (I, RegisterPage) => {
    RegisterPage.goto();
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("/login")
    I.see("Login");
});

const u1 = genuser();
Scenario('Both passwords dont match', (I, RegisterPage) => {
    RegisterPage.goto();
    RegisterPage.register(u1.firstname, u1.lastname, `${u1.email}`, 'miss', 'match');
    I.seeElement('.error');
    I.see('Error: Password and password repeat must be the same');
});

const u2 = genuser();
Scenario('Created successfully', (I, LoginPage, RegisterPage) => {
    RegisterPage.goto();
    RegisterPage.register(u2.firstname, u2.lastname, u2.email, u2.password, u2.password);
    I.dontSeeElement('.error');
    LoginPage.success(u2.email);
    I.amOnPage("/questions");
});

Scenario('Email already used', (I, RegisterPage) => {
    RegisterPage.goto();
    RegisterPage.register(u2.firstname, u2.lastname, u2.email, u2.password, u2.password);
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});