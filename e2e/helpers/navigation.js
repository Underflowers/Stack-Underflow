const { I } = inject();

module.exports = {
    landOnPageSafely(relativeURL, title) {
        I.amOnPage(relativeURL);
        I.see(title);
        I.dontSeeElement('.error');
    },
}
