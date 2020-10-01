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
    I.fillField('Firstname', 'John');
    I.fillField('Lastname', 'Doe');
    I.fillField('Email', 'john.doe@me.com');
    I.fillField('Password', 'john');
    I.fillField('Repeat password', 'doe');
    I.click('Register');
    I.seeElement('.error');
    I.see('Error: Passwords don\'t match');
});

Scenario('Created successfully', (I) => {
    var firstname = faker.name.firstName();
    var lastname = faker.name.lastName();

    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('Firstname', firstname);
    I.fillField('Lastname', lastname);
    I.fillField('Email', firstname + "." + lastname + "@me.com");
    I.fillField('Password', 'pwd');
    I.fillField('Repeat password', 'pwd');
    I.click('Register');
    I.dontSeeElement('.error');
    I.see('Authenticated user: ' + firstname + ' ' + lastname);
    I.amOnPage("http://localhost:9080/StackUnderflow/questions");
});

Scenario('Email already used', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.dontSeeElement('.error');
    I.fillField('Firstname', 'test');
    I.fillField('Lastname', 'test');
    I.fillField('Email', 'test@email.com');
    I.fillField('Password', 'test');
    I.fillField('Repeat password', 'test');
    I.click('Register');
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});