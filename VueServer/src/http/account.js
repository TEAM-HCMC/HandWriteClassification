const axios = require('axios');
const baseUrl = require('../config/serverUrl.js');
const cookieUtils = require('../utils/cookie.js');

// SIGN UP
var signUp = function signUp(reqDto) {

  const reqHeader = {
    headers: {
      'Content-Type': 'application/json',
    }
  };
  return new Promise((resolve,reject)=>{
    axios.post(baseUrl + '/account/signup', reqDto, reqHeader)
      .then((res) => {
        resolve(res.data);
      })
      .catch((err)=>{
        reject(err.response.data);
      });

  });

}
// SIGN UP END

//GET ACCOUNT INFO
var getAccountInfo = function getAccountInfo(callback) {
  const reqHeader = {
    headers: {
      'Content-Type': 'application/json',
      'jwt': cookieUtils.getJwt(),
    }
  };
  var resDto = {
    email: "",
    name: ""
  }
  return new Promise((resolve, reject) => {
    axios.get(baseUrl + '/account', reqHeader)
      .then((res) => {
        resDto.email = res.data.email;
        resDto.name = res.data.name;
        resolve(resDto);
      })
      .catch((error) => {
        console.log(error);
      });
  });
}
//GET ACCOUNT INFO END

module.exports.signUp = signUp;
module.exports.getAccountInfo = getAccountInfo;
