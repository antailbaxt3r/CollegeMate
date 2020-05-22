// This file is used to initialize db and make associations
const Sequelize = require('sequelize');

const db = {};

// The cache configuration
var Redis = require('ioredis');
db.cache = Redis;

db.Sequelize = Sequelize;

db.Op = Sequelize.Op; // Very important
db.sequelize = require('../models/public/db')
db.public = require("./public/models");


module.exports = db;