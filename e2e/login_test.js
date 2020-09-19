Feature('login');

var faker = require('faker');
var firstName = faker.name.firstName();
var lastname = faker.name.lastName();
var email = firstName + "." + lastname + "@me.com";
var pass = "test";

Scenario('Register link redirect', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/login");
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
});

Scenario('Created successfully for login', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('Firstname', firstName);
    I.fillField('Lastname', lastname);
    I.fillField('Email', email);
    I.fillField('Password', pass);
    I.fillField('Repeat password', pass);
    I.click('Register');
    I.dontSeeElement('.error');
    I.see('User created: ' + firstName + ' ' + lastname);
    I.dontSee('0 registered users');
});

Scenario('Login successfully', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/login");
    I.see("Login");
    I.fillField('Email', email);
    I.fillField('Password', pass);
    I.click('Login');
});