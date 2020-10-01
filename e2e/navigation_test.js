Feature('navigation');

Scenario('From home to questions', (I) => {
    I.amOnPage("/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".questionsLink");
    I.amOnPage("/questions");
});

Scenario('From home to register', (I) => {
    I.amOnPage("/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".registerLink");
    I.amOnPage("/register");
});

Scenario('From home to login', (I) => {
    I.amOnPage("/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".loginLink");
    I.amOnPage("/login");
});