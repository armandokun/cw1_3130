//Import the express and url modules
const express = require('express');
const url = require("url");

//Status codes defined in external file
require('./http_status.js');

//The express module is a function. When it is executed it returns an app object
const app = express();

//Import the mysql module
const mysql = require('mysql');

//Create a connection object with the user details
const connectionPool = mysql.createPool({
    connectionLimit: 1,
    host: "192.168.64.2",
    user: "user",
    password: "password",
    database: "cw1_3130",
    debug: false
});

//Set up the application to handle GET requests sent to the user path
app.get('/phones/*', handleGetRequest);//Subfolders
app.get('/phones', handleGetRequest);

//Start the app listening on port 8080
app.listen(8080);


/* Handles GET requests sent to web service.
   Processes path and query string and calls appropriate functions to
   return the data. */
function handleGetRequest(request, response){
    //Parse the URL
    const urlObj = url.parse(request.url, true);

    //Extract object containing queries from URL object.
    const queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    const numItems = queries['num_items'];
    const offset = queries['offset'];

    //Split the path of the request into its components
    const pathArray = urlObj.pathname.split("/");

    //Get the last part of the path
    const pathEnd = pathArray[pathArray.length - 1];

    //If path ends with 'phones' we return all phones
    if(pathEnd === 'phones'){
        getTotalPhonesCount(response, numItems, offset);//This function calls the getAllPhones function in its callback
        return;
    }

    //If path ends with phones/, we return all phones
    if (pathEnd === '' && pathArray[pathArray.length - 2] === 'phones'){
        getTotalPhonesCount(response, numItems, offset);//This function calls the getAllCereals function in its callback
        return;
    }

    //If the last part of the path is a valid user id, return data about that user
    const regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
    if(regEx.test(pathEnd)){
        getPhone(response, pathEnd);
        return;
    }

    //The path is not recognized. Return an error message
    response.status(HTTP_STATUS.NOT_FOUND);
    response.send("{error: 'Path not recognized', url: " + request.url + "}");
}


/** Returns all of the phones, possibly with a limit on the total number of items returned and the offset (to
 *  enable pagination). This function should be called in the callback of getTotalPhonesCount  */
function getAllPhones(response, totNumItems, numItems, offset){
    //Select the cereals data using JOIN to convert foreign keys into useful data.
    let sql = "SELECT phones.id, products.description, products.image_url, phones.price, url.product_url " +
        "FROM ( (phones INNER JOIN products ON phones.product_id=products.id) " +
        "INNER JOIN url ON phones.url_id=url.id ) ";

    //Limit the number of results returned, if this has been specified in the query string
    if(numItems !== undefined && offset !== undefined ){
        sql += "ORDER BY phones.id LIMIT " + numItems + " OFFSET " + offset;
    }

    //Execute the query
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Create JavaScript object that combines total number of items with data
        const returnObj = {totNumItems: totNumItems};
        returnObj.data = result; //Array of data from database

        //Return results in JSON format
        response.json(returnObj);
    });
}


/** When retrieving all phones we start by retrieving the total number of phones
    The database callback function will then call the function to get the phone data
    with pagination */
function getTotalPhonesCount(response, numItems, offset){
    const sql = "SELECT COUNT(*) FROM phones";

    //Execute the query and call the anonymous callback function.
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Get the total number of items from the result
        const totNumItems = result[0]['COUNT(*)'];

        //Call the function that retrieves all cereals
        getAllPhones(response, totNumItems, numItems, offset);
    });
}


/** Returns the cereal with the specified ID */
function getPhone(response, id){
    //Build SQL query to select cereal with specified id.
    const sql = "SELECT phones.id, products.description, products.image_url, phones.price " +
        "FROM ( (phones INNER JOIN products ON phones.product_id=products.id) " +
        "INNER JOIN url ON phones.url_id=url.id ) " +
        "WHERE phones.id=" + id;

    //Execute the query
    connectionPool.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Output results in JSON format
        response.json(result);
    });
}
