Feature('login');

const genuser = require("./helpers/genuser");

const u = genuser();
Scenario('Register link redirect', (I, LoginPage, RegisterPage) => {
    LoginPage.goto();
    I.see("Don't have an account?");
    I.clickLink("Don't have an account?");
    RegisterPage.goto();
});

Scenario('Created successfully for login', (I, LoginPage, RegisterPage) => {
    RegisterPage.goto();
    RegisterPage.register(u.firstname, u.lastname, u.email, u.password, u.password);
    I.dontSeeElement('.error');
    LoginPage.success(u.email);
});

Scenario('Login successfully', (LoginPage) => {
    LoginPage.goto();
    LoginPage.login(u.email, u.password);
    LoginPage.success(u.email);
});

Scenario('Login failed', (LoginPage) => {
    LoginPage.goto();
    LoginPage.login("hello@@stackunderflow.e2e", "nopass");
    LoginPage.failed();
});