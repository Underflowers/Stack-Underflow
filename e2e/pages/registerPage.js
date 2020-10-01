const { I } = inject();

module.exports = {
    fields: {
        firstname: "firstname",
        lastname: "lastname",
        email: "email",
        password: "password",
        passwordRepeat: "passwordRepeat",
    },
    submitButton: "registerBtn",

    fillAndRegisterUser(firstname, lastname, email, pwd, pwdRepeat) {
        I.fillField(this.fields.firstname, firstname);
        I.fillField(this.fields.lastname, lastname);
        I.fillField(this.fields.email, email);
        I.fillField(this.fields.password, pwd);
        I.fillField(this.fields.passwordRepeat, pwdRepeat);
        I.click(this.submitButton);
    }

}
