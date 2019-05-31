<style lang="css" scoped>

.signup_form {
    font-family: 'BMHANNAPro';
    padding-top: 20vh;
    width: 50%;
    margin: 0 auto;
}

.container {
    width: 100%;
}

.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .5);
    display: table;
    transition: opacity .3s ease;
}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;
}

.modal-container {
    width: 300px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    transition: all .3s ease;
    font-family: Helvetica, Arial, sans-serif;
}

.modal-header h3 {
    margin-top: 0;
    color: #42b983;
}

.modal-body {
    margin: 20px 0;
}

.modal-default-button {
    float: right;
}

.modal-footer{
  text-align: center;
}

.modal-footer>slot{
  cursor: pointer;
}
/*
* The following styles are auto-applied to elements with
* transition="modal" when their visibility is toggled
* by Vue.js.
*
* You can easily play with the modal transition by editing
* these styles.
*/

.modal-enter {
    opacity: 0;
}

.modal-leave-active {
    opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
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
                <form id="signUp-form">
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
        <myModal v-if="showModalSignUp" v-on:click="modalOk">
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
        email: null,
        name: null,
        password: null
      },
      password: '',
      showModalSignUp: false,
      headMessage: "",
      bodyMessage: "",
      tailMessage: "",
      isSuccess: false,
    }
  },

  methods: {
    signUp() {
      console.log(this.accountSaveReqDto);
      console.log(this.accountSaveReqDto.email);
      if (this.validate()) {
        account.signUp(this.accountSaveReqDto).then((data) => {
            if (data) {
              this.headMessage = "회원가입 성공";
              this.bodyMessage = "회원가입에 성공하였습니다.";
              this.isSuccess = true;
            }
          })
          .catch((data) => {
            this.headMessage = "회원가입 실패";
            this.bodyMessage = data.message;
            this.isSuccess = false;
          })
          .then(()=>{
            this.tailMessage = "확인";
            this.callModal()
          });
      }else{
        this.callModal()
      }

    },

    validate() {
      this.headMessage = "회원가입 실패";
      if (this.password !== this.accountSaveReqDto.password) {
        this.bodyMessage = "비밀번호가 일치하지 않습니다.";
        this.tailMessage = "비밀번호를 확인해 주세요.";
        this.isSuccess=false;
        return false;
      }
      return true;
    },

    callModal(){
      this.showModalSignUp=true;
    },

    modalOk() {
      this.showModalSignUp = false;
      console.log(this.isSuccess);
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
