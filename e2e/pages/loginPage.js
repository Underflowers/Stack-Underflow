const { I } = inject();

module.exports = {

    loginAs(email, pwd) {
        I.fillField('email', email);
        I.fillField('password', pwd);
        I.click('loginBtn');
    }

}
