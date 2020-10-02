const { I } = inject();

module.exports = {
    fields: {
        email: 'email',
        password: 'password',
    },
    submitButton: 'loginBtn',

    loginAs(email, pwd) {
        I.fillField(this.fields.email, email);
        I.fillField(this.fields.email, pwd);
        I.click(this.submitButton);
    }

}
