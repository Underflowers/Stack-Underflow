const { I } = inject();

module.exports = {

    landOnPageSafely(relativeURL, title) {
        I.amOnPage(relativeURL);
        I.see(title);
        I.dontSeeElement('.error');
    },

    checkLoggedIn(firstname, lastname) {
        I.see('Authenticated user: ' + firstname + ' ' + lastname);
    }

}
