module.exports = function genuser() {
    const fname = 'Jane';
    const lname = 'Doe';
    return {
        firstname: fname,
        lastname: lname,
        email: `${fname}.${lname}_${Date.now()}_${Math.random()}@stackunderflow.e2e`,
        password: 'security-is-very-important'
    };
};
