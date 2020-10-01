Feature('login');

var faker = require('faker');
var firstName = faker.name.firstName();
var lastname = faker.name.lastName();
var email = firstName + "." + lastname + "@me.com";
var pass = "test";

Scenario('Register link redirect', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/login");
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
});

Scenario('Created successfully for login', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('firstname', firstName);
    I.fillField('lastname', lastname);
    I.fillField('email', email);
    I.fillField('password', pass);
    I.fillField('passwordRepeat', pass);
    I.click('registerBtn');
    I.dontSeeElement('.error');
    I.see('Authenticated user: ' + firstName + ' ' + lastname);
});

Scenario('Login successfully', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/login");
    I.see("Login");
    I.fillField('email', email);
    I.fillField('password', pass);
    I.click('loginBtn');
    I.amOnPage('http://localhost:9080/StackUnderflow/questions');
});

Scenario('Login failed', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/login");
    I.see("Login");
    I.fillField('email', "hello@hello.com");
    I.fillField('password', "nopass");
    I.click('loginBtn');
    I.amOnPage('http://localhost:9080/StackUnderflow/login');
});