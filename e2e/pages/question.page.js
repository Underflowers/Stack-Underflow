const { I, Navigation } = inject();

module.exports = {
    fields: {
        answer: 'content',
        comment: 'comment'
    },
    submit: {
        answer: 'submitBtn',
        comment: 'commentBtn',
    },

    answer(message) {
        I.fillField(this.fields.answer, message);
        I.click(this.submit.answer);
        I.see(message);
    },

    comment(message) {
        I.fillField(this.fields.comment, message);
        I.click(this.submit.comment);
        I.see(message);
    }
}
