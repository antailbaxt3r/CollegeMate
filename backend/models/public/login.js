const db = require("../public/db");
const sequelize = db.sequelize;
const DataTypes = db.Sequelize;

module.exports.Login = sequelize.define('login', {
    id: {
        type: DataTypes.BIGINT,
        primaryKey: true,
        allowNull: false,
        autoIncrement: true
    },
    // org_id: { type:DataTypes.BIGINT }, 
    name: { type: DataTypes.TEXT },
    email: { type: DataTypes.TEXT },
    phone: { type: DataTypes.TEXT },
    year_of_study: { type: DataTypes.INTEGER },
    enrollment_id: { type: DataTypes.TEXT },
    new_user: {
        type: DataTypes.BOOLEAN,
        defaultValue: true
    },
    created_at: {
        type: DataTypes.DATE,
        allowNull: false,
        defaultValue: sequelize.literal('CURRENT_TIMESTAMP')
    },
    updated_at: DataTypes.DATE,
    deleted_at: DataTypes.DATE    
}, {
    underscored: true
});