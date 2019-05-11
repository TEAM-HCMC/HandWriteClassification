<style lang="css" scoped>

.sign{
  display: inline-block;
}

</style>

<template lang="html">

<div class="login_form">
    <div class="input_id">
        ID :
        <input type="text" placeholder="email을 입력하세요." v-model="ACCOUNT_EMAIL">

    </div>
    <div class="input_password">
        PW :
        <input type="password" v-model="ACCOUNT_PW">
    </div>
    <div class="sign">
      <button v-on:click="login">로그인</button>
      <button v-on:click="signUp">회원가입</button>
    </div>
</div>

</template>

<script>

const account = require('../../../../http/account.js');

export default {
    data() {
            return {
                ACCOUNT_EMAIL: '',
                ACCOUNT_PW: '',
            }
        },

        methods: {
            login() {
                account.login(this.ACCOUNT_EMAIL, this.ACCOUNT_PW);
            },

            signUp(){
              
            }
        }
}

</script>
