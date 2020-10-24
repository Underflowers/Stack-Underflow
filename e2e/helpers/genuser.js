const faker = require('faker');

module.exports = function genuser() {
    const fname = "Jane";
    const lname = "Doe";
    return {
        firstname: fname,
        lastname: lname,
        email: `${fname}.${lname}_${Date.now()}@stackunderflow.e2e`
    };
};
