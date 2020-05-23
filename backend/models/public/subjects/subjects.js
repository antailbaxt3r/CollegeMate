const db = require("../db");
const sequelize = db.sequelize;
const DataTypes = db.Sequelize;

module.exports.Subjects = sequelize.define('subject', {
    subject_id: {
        type: DataTypes.BIGINT,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
    },
    subject_title: {
        type: DataTypes.TEXT,
        allowNull: false
    },
    course_code: { 
        type: DataTypes.TEXT,
        allowNull:false
    },
    created_at: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: sequelize.literal('CURRENT_TIMESTAMP')
    },
    updated_at: DataTypes.DATE,
    deleted_at: DataTypes.DATE
},  {
    underscored: true
});