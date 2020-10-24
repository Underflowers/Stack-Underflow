const faker = require('faker');

module.exports = function genuser() {
    const fname = faker.name.firstName();
    const lname = faker.name.lastName();
    return {
        firstname: fname,
        lastname: lname,
        email: `${fname}.${lname}_${faker.random.number()}@stackunderflow.e2e`
    };
};
