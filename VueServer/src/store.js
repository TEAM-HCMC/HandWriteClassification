import Vue from 'vue'
import Vuex from 'vuex'
const axios = require("axios")
const baseUrl = require("./config/serverUrl")
const cookieUtils = require('./utils/cookie')

const httpAccount = require('./http/account')

Vue.use(Vuex);

export const store = new Vuex.Store({
  state: {
    email: null,
    name: null,
  },

  getters: {
    getEmail: function(state) {
      return state.email;
    },
    getName: function(state) {
      return state.name;
    }
  },

  mutations: {
    setLoginStatus(state, payload) {
      state.email = payload.email;
      state.name = payload.name;
    },

    getAccountInfo: function(callback, state) {
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
            this.commit('clearState');
            console.log(error);
          });
      });

    },

    login: function(state, payload) {

    },

    loginCheck: function(state) {
      if (cookieUtils.getJwt !== null) {
          httpAccount.getAccountInfo().then((resDto)=>{
            this.commit('setLoginStatus',resDto);
          });
      } else {
        this.commit('clearState');
      }
    },

    logout: function(state) {
      this.commit("clearState");
      cookieUtils.deleteCookie('jwt');
      console.log("로그아웃");
      location.reload();
    },

    clearState: function(state) {
      this.state.email = "";
      this.state.name = ""
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
      var resDto;
      var message = "로그인 실패";
      return axios.post(baseUrl + '/account/login', reqDto, config)
        .then((res) => {
          httpAccount.getAccountInfo().then(function(resDto){
            resDto = this.resDto;
            console.log(resDto);
          });
          cookieUtils.setCookie('jwt', res.data.jwt);
          message = "로그인 성공";
        })
        .catch((error) => {
          cookieUtils.deleteCookie('jwt');
          console.log(error);
        })
        .then(() => {
          console.log(message);
          console.log(resDto);
        });

    },

    getAccountInfo: function(context) {
      return context.commit('getAccountInfo');
    },

  },

});
