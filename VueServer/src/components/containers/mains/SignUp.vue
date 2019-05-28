<style lang="css" scoped>

.signup_form {
    padding-top: 20vh;
    width: 50%;
    margin: 0 auto;
}

.container {
    width: 100%;
}

#login_form {
    display: inline-block;
}

</style>

<template lang="html">

<div class="signup_form">
    <div class="container">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="panel-title">회원가입</div>
            </div>
            <div class="panel-body">
                <form id="login-form">
                    <div>
                        <input type="text" class="form-control" name="username" placeholder="Email" v-model="accountSaveReqDto.email" autofocus>
                    </div>
                    <div>
                        <input type="text" class="form-control" name="modelname" placeholder="Model Name" v-model="accountSaveReqDto.name">
                    </div>
                    <div>
                        <input type="password" class="form-control" name="password" placeholder="Password" v-model="accountSaveReqDto.password">
                    </div>
                    <div>
                        <input type="password" class="form-control" name="password_ok" placeholder="Password_Ok" v-model="password">
                    </div>
                    <div>
                        <br>
                        <button v-on:click="signUp" class="form-control btn btn-primary">회원가입</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="myModal">
        <myModal v-if="showModal" v-on:click="modalOk">
            <h3 slot="header">{{headMessage}}</h3>
            <span slot="body">{{bodyMessage}}</span>
            <span slot="footer" v-on:click="modalOk">
      {{tailMessage}}
      <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
    </span>
        </myModal>
    </div>

</div>

</template>

<script>
import myModal from '../../../utils/Modal.vue'
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
      isSuccess: false,
    }
  },

  methods: {
    signUp() {
      if (this.validate()) {

        account.signUp(this.accountSaveReqDto).then((data) => {
            if (data) {
              this.headMessage = "회원가입 성공";
              this.bodyMessage = "회원가입에 성공하였습니다.";
              this.tailMessage = "확인";
              this.isSuccess = true;
              this.showModal = true;
            }
          })
          .catch((data) => {
            this.headMessage = "회원가입 실패";
            this.bodyMessage = data.message;
            this.tailMessage = "확인";
            this.isSuccess = false;
            this.showModal = true;
          });
      }

    },

    validate() {
      this.headMessage = "회원가입 실패";
      if (this.password !== this.accountSaveReqDto.password) {
        this.bodyMessage = "비밀번호가 일치하지 않습니다.";
        this.tailMessage = "비밀번호를 확인해 주세요.";
        this.showModal = true;
        return false;
      }
      return true;
    },

    modalOk() {
      this.showModal = false;
      if (this.isSuccess) {
        this.$router.push("/");
      }
    }
  },
  components: {
    myModal,
  }

}
</script>
