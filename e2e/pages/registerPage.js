const { I } = inject();

module.exports = {

    fillAndRegisterUser(firstname, lastname, email, pwd, pwdRepeat) {
        I.fillField('firstname', firstname);
        I.fillField('lastname', lastname);
        I.fillField('email', email);
        I.fillField('password', pwd);
        I.fillField('passwordRepeat', pwdRepeat);
        I.click('registerBtn');
    }

}
