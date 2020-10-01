var faker = require('faker');

Feature('register');

Scenario('Login link redirect', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("http://localhost:9080/StackUnderflow/login")
    I.see("Login");
});

Scenario('Both passwords dont match', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('firstname', 'John');
    I.fillField('lastname', 'Doe');
    I.fillField('email', 'john.doe@me.com');
    I.fillField('password', 'john');
    I.fillField('passwordRepeat', 'doe');
    I.click('registerBtn');
    I.seeElement('.error');
    I.see('Error: Passwords don\'t match');
});

Scenario('Created successfully', (I) => {
    var firstname = faker.name.firstName();
    var lastname = faker.name.lastName();

    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('firstname', firstname);
    I.fillField('lastname', lastname);
    I.fillField('email', firstname + "." + lastname + "@me.com");
    I.fillField('password', 'pwd');
    I.fillField('passwordRepeat', 'pwd');
    I.click('registerBtn');
    I.dontSeeElement('.error');
    I.see('Authenticated user: ' + firstname + ' ' + lastname);
    I.amOnPage("http://localhost:9080/StackUnderflow/questions");
});

Scenario('Email already used', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('firstname', 'test');
    I.fillField('lastname', 'test');
    I.fillField('email', 'test@email.com');
    I.fillField('password', 'test');
    I.fillField('passwordRepeat', 'test');
    I.click('registerBtn');
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});