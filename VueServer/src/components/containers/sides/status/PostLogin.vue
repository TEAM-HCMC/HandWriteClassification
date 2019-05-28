<style lang="css" scoped>



</style>

<template lang="html">

<div class="login_form">

    <div class="account_id">
        아이디 : {{email}}

    </div>
    <div class="account_name">
        모델 이름 : {{name}}
    </div>
    <button v-on:click="logout">로그아웃</button>

    <div class="status">

        <div class="trainImage">
            <br />검증 기준 이미지<br />
            <span>입력여부 :</span><br />
            <span>입력시간 :</span><br /><br />
        </div>

        <div class="compareImage">
            검증 대상 이미지<br />
            <span>입력여부 :</span><br />
            <span>입력시간 :</span><br /><br />
        </div>

        <div class="trainModel">
            검증 모델<br />
            <span>생성여부 : {{status.trainModelFlag}}</span><br />
            <span>생성시간 : {{status.trainModel}}</span><br /><br />
        </div>

    </div>

</div>

</template>

<script>
import {
  mapGetters
}
from 'vuex'
const httpAccount = require('../../../../http/account');

export default {
  computed: mapGetters({
    getEmail: 'getEmail',
    getName: 'getName'
  }),

  data() {
    return {
      email: null,
      name: null,
      status:{
        trainImage:null,
        compareImage:null,
        trainModel:null,
        trainModelFlag:null,
      }
    }
  },

  created() {
    httpAccount.getAccountInfo().then((reqDto) => {
      this.email = reqDto.email;
      this.name = reqDto.name;
      var log = reqDto.log;
      console.log(reqDto);
      this.status.trainModel = log.train.created;
      this.status.trainModelFlag = log.train.flag;
    })
  },

  methods: {
    logout() {
      this.$store.commit('logout');
    }
  }
}
</script>
