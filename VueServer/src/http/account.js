const axios = require('axios');
const baseUrl = require('../config/testUrl.js');
const cookieUtils = require('../utils/cookie.js');

// SIGN UP
var signUp = function signUp(email, name, password) {

  const reqDto = {
    'email': email,
    'name': name,
    'password': password
  };

  const reqHeader = {
    headers: {
      'Content-Type': 'application/json',
    }
  };

  axios.post(baseUrl + '/account/login', reqDto, config)
    .then((res) => {
      console.log("회원가입 성공");
      location.reload();
    })
    .catch((err) => {
      console.log("회원가입 실패");
    });

}
// SIGN UP END

// GET INFO
var getAccountInfo = function getAccountInfo(jwt) {

  const reqHeader = {
    headers: {
      'Content-Type': 'application/json',
      'jwt': jwt,
    }
  };

  axios.get(baseUrl + '/account', reqHeader)
    .then((res) => {
      //TODO 언젠가 바꿔야함 일단 이대로 간다;
      localStorage.setItem('email', res.data.email);
      localStorage.setItem('name', res.data.name);
      location.reload();
    })
    .catch((error) => {
      localStorage.clear();
      console.log(error);
    });
};
// GET INFO END

// lOGIN
var login = function login(email, password) {

  let reqDto = {
    'email': email,
    'password': password
  };

  let config = {
    headers: {
      'Content-Type': 'application/json',
    }
  };

  axios.post(baseUrl + '/account/login', reqDto, config)
    .then((res) => {

      cookieUtils.setCookie('jwt', res.data.jwt);

      getAccountInfo(res.data.jwt);

      console.log("로그인 성공");

    })
    .catch((error) => {
      console.log("로그인 실패");
      localStorage.clear();
      cookieUtils.deleteCookie('jwt');
      console.log(error);
    });
};
// lOGIN END

module.exports.login = login;
module.exports.getAccountInfo = getAccountInfo;
module.exports.signUp = signUp;
