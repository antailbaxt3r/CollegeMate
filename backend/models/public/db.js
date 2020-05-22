'use strict'
// This file is used to initialize sequelize

const Sequelize = require('sequelize');
var config = require("../../config/config");

var env = config.db.env;

const sequelize = new Sequelize(env.DATABASE_NAME, env.DATABASE_USERNAME, env.DATABASE_PASSWORD, {
  host: env.DATABASE_HOST,
  port: env.DATABASE_PORT,
  dialect: env.DATABASE_DIALECT,
  define: {
    underscored: true,
    schema: env.SCHEMA
  },
  // socketPath : env.SOCKET_PATH,
  // dialectOptions: env.DIALECT_OPTIONS
//  logging: false
});

// Connect all the models/tables in the database to a db object,
// so everything is accessible via one object
const db = {};

db.connectDb = async() => {
  try {
      await sequelize.authenticate()
      console.log('Connected to postgresDB!')
  } catch (err) {
      console.log(err)
      console.log('Could not connect to postgresdb :(')
  }
}

db.Sequelize = Sequelize;
db.sequelize = sequelize;
db.Op = Sequelize.Op;

module.exports = db;
