Feature('navigation');

Scenario('From home to questions', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".questionsLink");
    I.amOnPage("http://localhost:9080/StackUnderflow/questions");
});

Scenario('From home to register', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".registerLink");
    I.amOnPage("http://localhost:9080/StackUnderflow/register");
});

Scenario('From home to login', (I) => {
    I.amOnPage("http://localhost:9080/StackUnderflow/home");
    I.see("Welcome to Stack Overflow - Home");
    I.clickLink(".loginLink");
    I.amOnPage("http://localhost:9080/StackUnderflow/login");
});