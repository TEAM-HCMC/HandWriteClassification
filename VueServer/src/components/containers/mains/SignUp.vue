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
  </div>

</template>

<script>
const account = require('../../../http/account.js');

export default {
  data(){
    return {
      accountSaveReqDto:{
        email:'',
        name:'',
        password:''
      },
      password:'',
    }
  },

  methods:{
    signUp() {
        account.signUp(this.accountSaveReqDto);
    }
  }

}

</script>
