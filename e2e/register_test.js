const faker = require('faker');

Feature('register');

Scenario('Login link redirect', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
    I.see("Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("/login")
    I.see("Login");
});

Scenario('Both passwords dont match', (I, registerPage) => {
    I.amOnPage("/register");
    I.see("Register");
    I.dontSeeElement('.error');
    registerPage.fillAndRegisterUser('John', 'Doe', 'john.doe@me.com', 'john', 'doe');
    I.seeElement('.error');
    I.see('Error: Passwords don\'t match');
});

Scenario('Created successfully', (I, registerPage) => {
    const firstname = faker.name.firstName();
    const lastname = faker.name.lastName();
    const email = firstname + "." + lastname + '@me.com';

    I.amOnPage("/register");
    I.see("Register");
    I.dontSeeElement('.error');
    registerPage.fillAndRegisterUser(firstname, lastname, email, 'pwd', 'pwd');
    I.dontSeeElement('.error');
    I.see('Authenticated user: ' + firstname + ' ' + lastname);
    I.amOnPage("/questions");
});

Scenario('Email already used', (I, registerPage) => {
    I.amOnPage("/register");
    I.see("Register");
    I.dontSeeElement('.error');
    registerPage.fillAndRegisterUser('test', 'test', 'test@email.com', 'test', 'test');
    I.seeElement('.error');
    I.see('Error: Email address already in use!');
});