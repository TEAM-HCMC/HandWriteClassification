import Vue from 'vue'
import Vuex from 'vuex'
const axios = require("axios")
const baseUrl = require("./config/serverUrl")
const cookieUtils = require('./utils/cookie')

const httpAccount = require('./http/account')

Vue.use(Vuex);

export const store = new Vuex.Store({

  mutations: {

    logout: function(state) {
      cookieUtils.deleteCookie('jwt');
      console.log("로그아웃");
      location.reload();
    },

  },

  actions: {
    login: function(context, loginReqDto) {

      let config = {
        headers: {
          'Content-Type': 'application/json',
        }
      };

      var reqDto = {
        email: loginReqDto.email,
        password: loginReqDto.password
      };
      var message = "로그인 실패";

      return axios.post(baseUrl + '/account/login', reqDto, config)
        .then((res) => {
          cookieUtils.setCookie('jwt', res.data.jwt);
          message = "로그인 성공";
        })
        .catch((error) => {
          cookieUtils.deleteCookie('jwt');
          console.log(error);
        })
        .then(() => {
          console.log(message);
          location.reload();
        });

    },

  }


});
