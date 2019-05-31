import Vue from 'vue'
import Vuex from 'vuex'
const axios = require("axios")
const baseUrl = require("./config/serverUrl")
const cookieUtils = require('./utils/cookie')

const httpAccount = require('./http/account')

Vue.use(Vuex);

export const store = new Vuex.Store({

  state: {
    email:null,
    name:null,
    status: {
      trainImageFlag: null,
      trainImageCreated: null,
      compareImageFlag: null,
      compareImageCreated: null,
      trainModelFlag: null,
      trainModelCreated: null,
      compareModelFlag:null,
      compareModelCreated:null,
      modelAccuracy: null,
    },
  },

  mutations: {

    //setter
    setEmail(state, payload) {
      return state.email = payload.email;
    },
    setName(state, payload) {
      return state.name = payload.name;
    },
    //status
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
    setCompareModelFlag(state, payload) {
      return state.status.compareModelFlag = payload.flag;
    },
    setCompareModelCreated(state, payload) {
      return state.status.compareModelCreated = payload.created;
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
    refresh: function (context) {

      httpAccount.getAccountInfo().then((reqDto) => {
        context.commit('setEmail',{email:reqDto.email});
        context.commit('setName',{name:reqDto.name});

        var log = reqDto.log;

        context.commit('setTrainImageFlag',{flag:log.trainContour.flag});
        context.commit('setTrainImageCreated',{created:log.trainContour.created});
        context.commit('setCompareImageFlag',{flag:log.compareContour.flag});
        context.commit('setCompareImageCreated',{created:log.compareContour.created});
        context.commit('setTrainModelFlag',{flag:log.train.flag});
        context.commit('setTrainModelCreated',{created:log.train.created});
        context.commit('setCompareModelFlag',{flag:log.compare.flag});
        context.commit('setCompareModelCreated',{created:log.compare.created});
        context.commit('setModelAccuracy',{accuracy:reqDto.model.accuracy});

      });

    },

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
