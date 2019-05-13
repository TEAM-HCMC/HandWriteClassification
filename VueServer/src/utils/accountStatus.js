const cookieUtils = require('./cookie')

var autoCheck = function setLoginStatus() {
  if(cookieUtils.getJwt() !== null){
    const jwt = cookieUtils.getJwt();
    const email = localStorage.getItem('email');
    const name = localStorage.getItem('name');
    console.log("자동로그인 성공");
  }else{
    localStorage.clear();
  };
};

var logout = function logout() {
    localStorage.clear();
    cookieUtils.deleteCookie('jwt');
    console.log("로그아웃");
    location.reload();
}

module.exports.autoCheck = autoCheck;
module.exports.logout = logout;
