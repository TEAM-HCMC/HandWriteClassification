<style lang="css" scoped>

.status>div{
  text-align: left;
}

.container {
    width: 100%;
}

.email, .name{
  text-align: left;
}

</style>

<template lang="html">

  <div class="container">
      <div class="panel panel-success">
          <div class="panel-heading">
              <div class="panel-title">환영합니다.</div>
          </div>
          <div class="panel-body">
              <form id="login-form">
                  <div class="email">
                      Email : {{email}}
                  </div>
                  <div class="name">
                      Model : {{name}}
                  </div>
                  <div>
                      <button v-on:click="logout" class="form-control btn btn-primary">로그아웃</button>
                  </div>
                  <div class="status">

                      <div class="trainImage">
                          <br />검증 기준 이미지
                          <br />
                          <span>입력여부 : {{status.trainImageFlag}}</span>
                          <br />
                          <span>입력시간 : {{status.trainImageCreated}}</span>
                          <br />
                          <br />
                      </div>

                      <div class="compareImage">
                          검증 대상 이미지
                          <br />
                          <span>입력여부 :{{status.compareImageFlag}}</span>
                          <br />
                          <span>입력시간 :{{status.compareImageCreated}}</span>
                          <br />
                          <br />
                      </div>

                      <div class="trainModel">
                          검증 모델
                          <br />
                          <span>생성여부 : {{status.trainModelFlag}}</span>
                          <br />
                          <span>생성시간 : {{status.trainModelCreated}}</span>
                          <br />
                          <br />
                      </div>

                  </div>
              </form>
          </div>
      </div>
  </div>

<!-- <div class="login_form">

    <div class="account_id">
        아이디 : {{email}}

    </div>
    <div class="account_name">
        모델 이름 : {{name}}
    </div>
    <button v-on:click="logout">로그아웃</button>

    <div class="status">

        <div class="trainImage">
            <br />검증 기준 이미지
            <br />
            <span>입력여부 : {{status.trainImageFlag}}</span>
            <br />
            <span>입력시간 : {{status.trainImageCreated}}</span>
            <br />
            <br />
        </div>

        <div class="compareImage">
            검증 대상 이미지
            <br />
            <span>입력여부 :{{status.compareImageFlag}}</span>
            <br />
            <span>입력시간 :{{status.compareImageCreated}}</span>
            <br />
            <br />
        </div>

        <div class="trainModel">
            검증 모델
            <br />
            <span>생성여부 : {{status.trainModelFlag}}</span>
            <br />
            <span>생성시간 : {{status.trainModelCreated}}</span>
            <br />
            <br />
        </div>

    </div>

</div> -->

</template>

<script>
import {
  mapGetters
}
from 'vuex'
const httpAccount = require('../../../../http/account');

const cookieUtils = require('./../../../../utils/cookie');
const jwt = cookieUtils.getJwt();

export default {
  computed: mapGetters({
    getEmail: 'getEmail',
    getName: 'getName'
  }),

  data() {
    return {
      email: null,
      name: null,
      status: {
        trainImageFlag: null,
        trainImageCreated: null,
        compareImageFlag: null,
        compareImageCreated: null,
        trainModelFlag: null,
        trainModelCreated: null,
      }
    }
  },

  created() {
    httpAccount.getAccountInfo().then((reqDto) => {
      this.email = reqDto.email;
      this.name = reqDto.name;
      var log = reqDto.log;
      this.status.trainImageFlag = log.contour.flag;
      this.status.trainImageCreated =log.contour.created;
      this.status.trainModelCreated = log.train.created;
      this.status.trainModelFlag = log.train.flag;
    })
  },

  // mounted(){
  //   this.autoRefresh();
  // },

  methods: {
    logout() {
      this.$store.commit('logout');
    },

    autoRefresh(){
      if(jwt!==null){
        setInterval(() => {
          httpAccount.getAccountInfo().then((reqDto) => {
            this.email = reqDto.email;
            this.name = reqDto.name;
            var log = reqDto.log;
            this.status.trainImageFlag = log.contour.flag;
            this.status.trainImageCreated = log.contour.created;
            this.status.trainModelCreated = log.train.created;
            this.status.trainModelFlag = log.train.flag;
          })
        }, 6000);
      }
    },

  }
}
</script>
