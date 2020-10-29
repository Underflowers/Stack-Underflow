const { I, Navigation } = inject();

module.exports = {
    fields: {
        title: 'title',
        content: 'content',
    },
    submitButton: 'submitBtn',

    goto() {
        Navigation.landOnPageSafely('/questions', 'All questions');
    },

    gotoFirstQuestion(title) {
        I.clickLink('div.card:first-of-type');
        I.see(title);
    }
}
