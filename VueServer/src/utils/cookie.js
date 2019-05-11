module.exports.setCookie = function(name, value) {
  var date = new Date();
  const day = 7;
  date.setTime(date.getTime() + day * 24 * 60 * 60 * 1000);
  document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};

module.exports.getCookie = function(name) {
  var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
  return value ? value[2] : null;
};


module.exports.deleteCookie = function(name) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
};

module.exports.getJwt = function() {
  var value = document.cookie.match('(^|;) ?' + 'jwt' + '=([^;]*)(;|$)');
  return value ? value[2] : null;
};
