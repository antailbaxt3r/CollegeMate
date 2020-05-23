var express = require('express');
var router = express.Router();

var verify = require('../functions/verifyFunc');
var validate = require('../functions/validateFunc');

var subjects = require('../controllers/subjectsCtrl')

router.get('/get', verify.user, subjects.getSubjects);
router.post('/add', verify.user, validate.subjectValidation, subjects.createSubject)

module.exports = router