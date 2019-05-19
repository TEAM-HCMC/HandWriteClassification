const axios = require('axios');
const baseUrl = require('../config/serverUrl.js');
const cookieUtils = require('../utils/cookie.js');

const reqHeader = {
  headers: {
    'Content-Type': 'application/json',
    'jwt': cookieUtils.getJwt(),
  }
};

var startTrain = function trainStart(initial) {

  const reqDto = {
    'name': initial,
  };

  axios.post(baseUrl + '/model/train', reqDto, reqHeader)
    .then((res) => {
      console.log("모델학습 실행 성공");
    })
    .catch((err) => {
      console.log("모델학습 실행 실패");
    });

}

var startCompare = function trainStart(initial) {

  const reqDto = {
    'name': initial,
  }

  axios.post(baseUrl + '/model/compare', reqDto, reqHeader)
    .then((res) => {
      console.log("검증 종료");
    })
    .catch((err) => {
      console.log("검증 실패");
    });

}

var getResult = function getResult(callback) {

  var resDto = {
    'correct':"", 
    'wrong':""
  }
 return new Promise(function(resolve,reject){
   axios.get(baseUrl + '/model/compare', reqHeader)
     .then((res) => {
       console.log(res);
       resDto.correct=res.data.correctRate;
       resDto.wrong=res.data.wrongRate;
       resolve(resDto);
     })
     .catch((err) => {
       console.log(err);
     });
 });

}



module.exports.startTrain = startTrain;
module.exports.startCompare = startCompare;
module.exports.getResult = getResult;
