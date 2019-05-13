<style lang="css" scoped>

.sign{
  display: inline-block;
}

</style>

<template lang="html">

<div class="login_form">
    <div class="input_id">
        ID :
        <input type="text" placeholder="email을 입력하세요." v-model="accountLoginReqDto.email">

    </div>
    <div class="input_password">
        PW :
        <input type="password" v-model="accountLoginReqDto.password">
    </div>
    <div class="sign">
      <button v-on:click="login">로그인</button>
      <router-link to="/signup">
        <button>회원가입</button>
      </router-link>
    </div>
</div>

</template>

<script>

const account = require('../../../../http/account.js');

export default {
    data() {
            return {
              accountLoginReqDto : {
                email : '',
                password : ''
              }
            }
        },

        methods: {
            login() {
                account.login(this.accountLoginReqDto);
            },
        }
}

</script>
