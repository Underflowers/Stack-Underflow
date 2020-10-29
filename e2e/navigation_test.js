Feature('navigation');

Scenario('From home to questions', (I, Navigation) => {
    Navigation.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Questions");
    I.amOnPage("/questions");
});

Scenario('From home to register', (I, Navigation) => {
    Navigation.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Register");
    I.amOnPage("/register");
});

Scenario('From home to login', (I, Navigation) => {
    Navigation.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Login");
    I.amOnPage("/login");
});

Scenario('From home to statistics', (I, Navigation) => {
    Navigation.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Statistics");
    I.amOnPage("/statistics");
});