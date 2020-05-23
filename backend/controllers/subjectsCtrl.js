const db = require("../models/db");
const fetch = require('../functions/fetchFunc');

module.exports.getSubjects = async (req,res) => {
    try {
        // Extract user id after authenticating
        const id = req.user.id;
        // Find complaint using fk login_id 
        const subjectData = await db.public.subjects.findAll({
            where: { login_id: id },
            attributes: ['subject_title', 'subject_id', 'course_code', 'updated_at', 'created_at']
            /* include: [{
                model: db.public.images,
                attributes: ['image']
            }] */
        });
        return res.status(200).json({
            success: true,
            subjects: subjectData, // If null, front-end should show no current compaints
        });
    } catch (err) {
        console.log(err);
        return res.status(500).json({
            success: false,
            msg: 'Internal server error'
        });
    }
}

module.exports.createSubject = async function (req, res) {
    try {
        // get request_body
        const subject = req.body;
        // const images = req.body.images;
        //change code for user_id
        subject.login_id = req.user.id;
        const newSubject = await db.public.subjects.create(subject, { returning: true });
        const subject_obj = fetch.include(newSubject, ['subject_title', 'course_code', 'subject_id', 'updated_at', 'created_at']);
        
            return res.status(200).json({
                success: true,
                subject: subject_obj
            });
    } catch (err) {
        console.log(err);
        return res.status(500).json({
            success: false,
            msg: 'Internal server error'
        });
    }
}