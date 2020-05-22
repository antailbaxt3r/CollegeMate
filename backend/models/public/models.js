const db = {};
// Define all your models here
 
//Models/tables
// User Profile
db.login = require('./login.js').Login;

module.exports = db