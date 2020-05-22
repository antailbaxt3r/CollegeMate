var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var session = require('express-session');

const port = 3000

var signInRouter = require('./routes/signin')
var app = express()

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));


app.get('/', (req, res) => {
    res.status(200).json({
        message: "Welcome to CollegeMate"
    })
})

app.use('/api', signInRouter);

app.listen(port, () => {
    console.log('App running on port', port)
})

