var { OAuth2Client } = require('google-auth-library');
var jwt = require('jsonwebtoken');

const config = require('../config/config')
const db = require('../models/db')

module.exports.checkUserGoogle = async(req, res) => {
    //const CLIENT_ID = '498233300103-3p9u6r2rmlru42i40d421ju1ljosdca9.apps.googleusercontent.com'
    const CLIENT_ID = config.google.CLIENT_ID;    
    const client = new OAuth2Client(CLIENT_ID);
    const idToken = req.body.idToken;
    if(idToken){
        try {
            const ticket = await client.verifyIdToken({
                idToken: idToken,
                audience: CLIENT_ID,  // Specify the CLIENT_ID of the app that accesses the backend
                // Or, if multiple clients access the backend:
                //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3]
            });
            // get the data from google
            const payload = ticket.getPayload();
            // const userid = payload['sub'];
            // If request specified a G Suite domain:
            //const domain = payload['hd'];
            const userEmail = payload['email'];
            const userName = payload['name'];
            // check if the user already exists in our database
            let user = await db.public.login.findOne({
                where: {
                   email: userEmail
                }, 
                attributes: ['id', 'email', 'created_at', 'new_user']
            })
            // console.log(user);
            if (!user) {
                // Create a new user
                var create_object = {
                    email: userEmail,
                    name: userName
                };
                
                db.public.login.create(create_object)
                    .then(login_data => {
                        // The payload of the auth-token
                        console.log("LOGIN DATA: ", login_data)
                        var auth_data = {
                            email: login_data.email,
                            id: login_data.id,
                            created_at: login_data.created_at
                        };
                        // Create and assign an auth-token
                        const TOKEN_SECRET = config.app.jwtKey
                        // var token = jwt.sign(auth_data, TOKEN_SECRET, { expiresIn: (amount of time for storing jwt token)})
                        var token = jwt.sign(auth_data, TOKEN_SECRET)
                        // console.log(login_data)
                        // console.log('new user')
                        // console.log(login_data.new_user);
                        return res.status(200).json({
                            success: true,
                            authToken: token,
                            newUser: login_data.new_user // newUser = true
                        });
                    })
                    .catch (err => {
                        console.log(err);
                        return res.status(500).json({
                            success: false,
                            msg: 'Internal server error'
                        });
                        
                    })
            } else if (user) {
                // The user has already signed-in through google
                // The payload of the auth-token
                var auth_data = {
                    email: user.email,
                    id: user.id,
                    created_at: user.created_at
                }
                // Create and assign an auth-token
                const TOKEN_SECRET = config.app.jwtKey
                var token = jwt.sign(auth_data, TOKEN_SECRET);
                // console.log(user)
                // console.log('Already exists.')
                // console.log(user.new_user);
                return res.status(200).json({
                    success: true,
                    authToken: token,
                    newUser: user.new_user
                });
            }
        } catch (err) {
            console.log(err);
            return res.status(500).json({
                success: false,
                error: 'Internal server error.'
            })
        }
    } else {
        return res.status(400).json({
            success: false,
            error: 'Idtoken not found.'
        })
    }   
}