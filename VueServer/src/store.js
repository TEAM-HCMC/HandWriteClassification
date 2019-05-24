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

      var resDto = {
        status:null,
        message:'로그인 성공',
      }

      return new Promise((resolve, reject) => {
        axios.post(baseUrl + '/account/login', reqDto, config)
          .then((res) => {
            cookieUtils.setCookie('jwt', res.data.jwt);
            resDto.status = res.status;
          })
          .catch((err) => {
            cookieUtils.deleteCookie('jwt');
            resDto.status = err.response.status;
            resDto.message = err.response.data.message;
          })
          .then(() => {
            resolve(resDto);
          });

      });


    },

  }


});
