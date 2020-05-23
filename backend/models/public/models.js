const db = {};
// Define all your models here
 
//Models/tables
// User Profile
db.login = require('./login.js').Login;

//subjects
db.subjects = require('./subjects/subjects').Subjects

module.exports = db