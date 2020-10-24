const { I } = inject();

module.exports = {

    landOnPageSafely(relativeURL, title) {
        I.amOnPage(relativeURL);
        I.see(title);
        I.dontSeeElement('.error');
    },

    checkLoggedIn(email) {
        I.see(email);
        I.see('Logout');
        this.landOnPageSafely("/questions", "All questions");
    }

}
