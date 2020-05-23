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

//subjects
db.public.login.hasMany(db.public.subjects, { foreignKey: 'login_id', onDelete: 'CASCADE', constraints: false, allowNull: false });
db.public.subjects.belongsTo(db.public.login, { foreignKey: 'user_id', onDelete: 'CASCADE', constraints: false, allowNull: false });

module.exports = db;