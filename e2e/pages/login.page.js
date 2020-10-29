const { I, Navigation } = inject();

module.exports = {
    fields: {
        email: 'email',
        password: 'password',
    },
    submitButton: 'loginBtn',

    goto() {
        Navigation.landOnPageSafely('/login', 'Login');
    },

    login(email, pwd) {
        I.fillField(this.fields.email, email);
        I.fillField(this.fields.password, pwd);
        I.click(this.submitButton);
    },

    success(email) {
        I.see(email);
        I.see('Logout');
    },

    failed() {
        I.amOnPage('/login');
    }
}
