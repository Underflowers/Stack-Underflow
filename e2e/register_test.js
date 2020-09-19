Feature('register');

Scenario('Login link redirect', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
    I.see("Already have an account?");
    I.clickLink("Already have an account?");
    I.amOnPage("http://localhost:8080/stack-underflow/login")
    I.see("Login");
});

Scenario('Both passwords dont match', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
    I.see("0 registered users");
    I.dontSeeElement('.error');
    I.fillField('Firstname', 'John');
    I.fillField('Lastname', 'Doe');
    I.fillField('Email', 'john.doe@me.com');
    I.fillField('Password', 'john');
    I.fillField('Repeat password', 'doe');
    I.click('Register');
    I.seeElement('.error');
    I.see('Error: Password and password repeat must correspond');
    I.see('0 registered users');
});

Scenario('Created successfully', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
    I.see("0 registered users");
    I.dontSeeElement('.error');
    I.fillField('Firstname', 'John');
    I.fillField('Lastname', 'Doe');
    I.fillField('Email', 'john.doe@me.com');
    I.fillField('Password', 'john');
    I.fillField('Repeat password', 'john');
    I.click('Register');
    I.dontSeeElement('.error');
    I.see('User created: John Doe');
    I.see('1 registered users');
});

Scenario('Email already used', (I) => {
    I.amOnPage("http://localhost:8080/stack-underflow/register");
    I.see("Register");
    I.see("1 registered users");
    I.dontSeeElement('.error');
    I.fillField('Firstname', 'John');
    I.fillField('Lastname', 'Doe');
    I.fillField('Email', 'john.doe@me.com');
    I.fillField('Password', 'john');
    I.fillField('Repeat password', 'john');
    I.click('Register');
    I.seeElement('.error');
    I.see('Error: Email already used');
    I.see('1 registered users');
});