<style lang="css" scoped>



</style>

<template lang="html">

  <div class="signup_form">
    <div class="input_id">
        ID :
        <input type="text" placeholder="email을 입력하세요." v-model="accountSaveReqDto.email">
    </div>
    <div class="input_name">
        NAME :
        <input type="text" placeholder="이니셜을 입력하세요." v-model="accountSaveReqDto.name">
    </div>
    <div class="input_password_1">
        PW :
        <input type="password" placeholder="비밀번호를 입력하세요." v-model="accountSaveReqDto.password">
    </div>
    <div class="input_password_2">
        PW :
        <input type="password" placeholder="비밀번호 확인." v-model="password">
    </div>
    <div class="submit">
      <button v-on:click="signUp">회원가입</button>
    </div>

    <div class="modal">
      <modal v-if="showModal" v-on:click="modalOk">
      <h3 slot="header">{{headMessage}}</h3>
      <span slot="body">{{bodyMessage}}</span>
      <span slot="footer" v-on:click="modalOk">
        {{tailMessage}}
        <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
      </span>
    </modal>
    </div>
  </div>



</template>

<script>
import modal from '../../../utils/Modal.vue'
const account = require('../../../http/account.js');

export default {
  data() {
    return {
      accountSaveReqDto: {
        email: '',
        name: '',
        password: ''
      },
      password: '',
      showModal: false,
      headMessage: "",
      bodyMessage: "",
      tailMessage: "",
      isSuccess:false,
    }
  },

  methods: {
    signUp() {
      account.signUp(this.accountSaveReqDto).then((data) => {
          if (data) {
            this.headMessage = "회원가입 성공";
            this.bodyMessage = "회원가입에 성공하였습니다.";
            this.tailMessage = "확인";
            this.isSuccess=true;
            this.showModal = true;
          }
        })
        .catch((data) => {
          this.headMessage = "회원가입 실패";
          this.bodyMessage = data.message;
          this.tailMessage = "확인";
          this.isSuccess=false;
          this.showModal = true;
        });
    },

    modalOk() {
      this.showModal = false;
      if(this.isSuccess){
        this.$router.push("/");
      }
    }
  },
  components:{
    modal,
  }

}
</script>
