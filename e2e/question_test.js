Feature('question');

const genuser = require("./helpers/genuser");

const title = 'New question';

const u = genuser();
Scenario('Created successfully for question', (I, LoginPage, RegisterPage) => {
    RegisterPage.goto();
    RegisterPage.register(u.firstname, u.lastname, u.email, u.password, u.password);
    I.dontSeeElement('.error');
    LoginPage.success(u.email);
});

Scenario('See questions list when not logged', (I, QuestionsPage) => {
    QuestionsPage.goto();
    I.dontSee('a[href="/ask"]');
});

Scenario('See questions list when logged', (I, LoginPage) => {
    LoginPage.goto();
    LoginPage.login(u.email, u.password);
    LoginPage.success(u.email);

    I.seeElement('a[href="/ask"]');
});

Scenario('Ask a question', (I, LoginPage, AskPage, QuestionsPage) => {
    LoginPage.goto();
    LoginPage.login(u.email, u.password);
    LoginPage.success(u.email);

    I.clickLink('a[href="/ask"]');
    AskPage.goto();
    AskPage.fillAndAskQuestion(title, 'Content');
    
    QuestionsPage.goto();
    I.see(title);
});

Scenario('See the detail of a question', (I, LoginPage, QuestionsPage) => {
    LoginPage.goto();
    LoginPage.login(u.email, u.password);
    LoginPage.success(u.email);

    QuestionsPage.gotoQuestion(title);
});