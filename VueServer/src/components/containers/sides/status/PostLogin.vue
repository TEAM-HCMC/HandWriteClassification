<style lang="css" scoped>

.status>div {
    text-align: left;
}

.container {
    font-family: 'BMHANNAPro';
    width: 100%;
}

.email,
.name {
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

                <div class="status">

                    <div class="trainImage">
                        <br />검증 기준 이미지
                        <br />
                        <span>입력여부 : {{this.$store.state.status.trainImageFlag}}</span>
                        <br />
                        <span>입력시간 : {{this.$store.state.status.trainImageCreated}}</span>
                        <br />
                        <br />
                    </div>

                    <div class="compareImage">
                        검증 대상 이미지
                        <br />
                        <span>입력여부 :{{this.$store.state.status.compareImageFlag}}</span>
                        <br />
                        <span>입력시간 :{{this.$store.state.status.compareImageCreated}}</span>
                        <br />
                        <br />
                    </div>

                    <div class="trainModel">
                        검증 모델
                        <br />
                        <span>생성여부 : {{this.$store.state.status.trainModelFlag}}</span>
                        <br />
                        <span>생성시간 : {{this.$store.state.status.trainModelCreated}}</span>
                        <br />
                        <span>정확도 : {{this.$store.state.status.modelAccuracy}}</span>
                        <br />
                        <br />
                    </div>

                </div>

                <div>
                    <button v-on:click="logout" class="form-control btn btn-primary">로그아웃</button>
                </div>
            </form>
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
    }
  },

  created() {
    httpAccount.getAccountInfo().then((reqDto) => {
      this.email = reqDto.email;
      this.name = reqDto.name;
      var log = reqDto.log;

      this.$store.commit('setTrainImageFlag',{flag:log.contour.flag});
      this.$store.commit('setTrainImageCreated',{created:log.contour.created});
      this.$store.commit('setCompareImageFlag',{flag:log.compare.flag});
      this.$store.commit('setCompareImageCreated',{created:log.compare.created});
      this.$store.commit('setTrainModelFlag',{flag:log.train.flag});
      this.$store.commit('setTrainModelCreated',{created:log.train.created});
      this.$store.commit('setModelAccuracy',{accuracy:reqDto.model.accuracy});

    })
  },

  mounted() {
    this.autoRefresh();
  },

  methods: {
    logout() {
      this.$store.commit('logout');
    },

    autoRefresh() {
      if (jwt !== null) {
        setInterval(() => {
          console.log("refresh");
          httpAccount.getAccountInfo().then((reqDto) => {
            this.email = reqDto.email;
            this.name = reqDto.name;
            var log = reqDto.log;
            console.log(reqDto);
            this.$store.commit('setTrainImageFlag',{flag:log.contour.flag});
            this.$store.commit('setTrainImageCreated',{created:log.contour.created});
            this.$store.commit('setCompareImageFlag',{flag:log.compare.flag});
            this.$store.commit('setCompareImageCreated',{created:log.compare.created});
            this.$store.commit('setTrainModelFlag',{flag:log.train.flag});
            this.$store.commit('setTrainModelCreated',{created:log.train.created});
            this.$store.commit('setModelAccuracy',{accuracy:reqDto.model.accuracy});

          })
        }, 6000);
      }
    },

  }
}
</script>
