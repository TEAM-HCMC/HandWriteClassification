const axios = require('axios');
const baseUrl = require('../config/serverUrl.js');
const cookieUtils = require('../utils/cookie.js');

const reqHeader = {
  headers: {
    'Content-Type': 'application/json',
    'jwt': cookieUtils.getJwt(),
  }
};

var startContour = function contour(direction) {
  const path = '/image/contour/' + direction;
  axios.post(baseUrl + path, {}, reqHeader)
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.log(err);
    });

};

var getComparedImgs = function getComparedImgs() {
  const path = '/image/compare'

  return new Promise((resolve, reject)=>{
    axios.get(baseUrl+path,reqHeader)
    .then((res=>{
      console.log(res);
      resolve(res.data);
    }))
    .catch((error) => {
      console.log(error);
    });
  });



}

module.exports.startContour=startContour;
module.exports.getComparedImgs=getComparedImgs;
