Feature('navigation');
const common = require("./pages/common");

Scenario('From home to questions', (I) => {
    common.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Questions");
    I.amOnPage("/questions");
});

Scenario('From home to register', (I) => {
    common.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Register");
    I.amOnPage("/register");
});

Scenario('From home to login', (I) => {
    common.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Login");
    I.amOnPage("/login");
});

Scenario('From home to statistics', (I) => {
    common.landOnPageSafely("/", "Welcome to Stack Underflow");
    I.clickLink("Statistics");
    I.amOnPage("/statistics");
});