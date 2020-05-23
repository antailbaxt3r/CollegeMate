// This function is used to fetch appropriate parameters from an object and return it

// (array) = ['paramter1', 'paramter2', etc]
module.exports.include = (object, parameters) => {
    if(Array.isArray(parameters) && parameters.length > 0) {
        let subset = parameters.reduce((acc, currval) => (
            acc[currval] = object[currval], acc // Constructing subset recursively
        ), {})
        return subset;
    }
}

// Do not use, seems to have certain issues
module.exports.exclude = (object, parameters) => {
    if(Array.isArray(parameters) && parameters.length > 0) {
        let subset = parameters.forEach(parameter => {
            delete object[parameter];
            console.log(object);
            return object;
        })
        console.log(subset);
        return subset;
    }
}

if (require.main === module) {
    var testObj = {
        id: "hi",
        name: "welcome",
        org: "to",
        '4': "test",
        '5': "for",
        '6': "fetch",
        '7': "function",
        human: "Arjun"
    };
    var testarray = ['id', '4', '7'];
    var includeObj = this.include(testObj, testarray);
    var excludeObj = this.exclude(testObj, testarray);
    console.log(includeObj);
    console.log(excludeObj);   
}