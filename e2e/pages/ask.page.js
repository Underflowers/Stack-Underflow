const { I, Navigation } = inject();

module.exports = {
    fields: {
        title: 'title',
        content: 'content',
    },
    submitButton: 'submitBtn',

    goto() {
        Navigation.landOnPageSafely('/ask', 'Ask your question');
    },

    fillAndAskQuestion(title, content) {
        I.fillField(this.fields.title, title);
        I.fillField(this.fields.content, content);
        I.click(this.submitButton);
    }

}
