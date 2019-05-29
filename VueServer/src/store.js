import Vue from 'vue'
import Vuex from 'vuex'
const axios = require("axios")
const baseUrl = require("./config/serverUrl")
const cookieUtils = require('./utils/cookie')

const httpAccount = require('./http/account')

Vue.use(Vuex);

export const store = new Vuex.Store({

  state: {
    status: {
      trainImageFlag: null,
      trainImageCreated: null,
      compareImageFlag: null,
      compareImageCreated: null,
      trainModelFlag: null,
      trainModelCreated: null,
      modelAccuracy: null,
    },
  },

  mutations: {

    //setter
    setTrainImageFlag(state, payload) {
      return state.status.trainImageFlag = payload.flag;
    },
    setTrainImageCreated(state, payload) {
      return state.status.trainImageCreated = payload.created;
    },
    setCompareImageFlag(state, payload) {
      return state.status.compareImageFlag = payload.flag;
    },
    setCompareImageCreated(state, payload) {
      return state.status.compareImageCreated = payload.created;
    },
    setTrainModelFlag(state, payload) {
      return state.status.trainModelFlag = payload.flag;
    },
    setTrainModelCreated(state, payload) {
      return state.status.trainModelCreated = payload.created;
    },
    setModelAccuracy(state, payload) {
      return state.status.modelAccuracy = payload.accuracy;
    },

    // //getter
    // getTrainImageFlag(state) {
    //   return state.status.trainImageFlag;
    // },
    // getTrainImageCreated(state) {
    //   return state.status.trainImageCreated;
    // },
    // getCompareImageFlag(state) {
    //   return state.status.compareImageFlag;
    // },
    // getCompareImageCreated(state) {
    //   return state.status.compareImageCreated;
    // },
    // getTrainModelFlag(state) {
    //   return state.status.trainModelFlag;
    // },
    // getTrainModelCreated(state) {
    //   return state.status.trainModelCreated;
    // },

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
        status: null,
        message: '로그인 성공',
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
