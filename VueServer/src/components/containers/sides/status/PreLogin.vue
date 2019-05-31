<style lang="css" scoped>

.preLogin {
    width: 100%;
}

.container {
    font-family: 'BMHANNAPro';
    width: 100%;
}

</style>

<template lang="html">

<div class="preLogin">

    <div class="container">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="panel-title">로그인</div>
            </div>
            <div class="panel-body">
                <form id="login-form">
                    <div>
                        <input type="text" class="form-control" name="username" placeholder="Email" v-model="accountLoginReqDto.email" autofocus>
                    </div>
                    <div>
                        <input type="password" class="form-control" name="password" placeholder="Password" v-model="accountLoginReqDto.password">
                    </div>
                    <div>
                        <br>
                        <button type="button" v-on:click="login" class="form-control btn btn-primary">로그인</button>
                        <br>
                        <br>
                        <router-link to="/signup">
                            <button type="button" class="form-control btn btn-primary">회원가입</button>
                        </router-link>

                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="myModal">
        <myModal v-if="showModal" v-on:click="modalOk">
            <h3 slot="header">경고</h3>
            <span slot="body">{{bodyMessage}}</span>
            <span slot="footer" v-on:click="modalOk">
        ID 및 PW를 확인하십시오.
        <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
      </span>
        </myModal>
    </div>

</div>

</template>

<script>
import myModal from '../../../../utils/Modal.vue'

export default {

  data() {
    return {
      accountLoginReqDto: {
        email: '',
        password: '',
      },
      showModal: false,
      bodyMessage: '',
    }
  },

  methods: {
    login() {
      console.log("로그인 중...");
      this.$store.dispatch('login', this.accountLoginReqDto)
        .then((resDto) => {
          if (resDto.status === 201) {
            location.reload();
            location.replace("/");
          } else {
            console.log("로그인실패.");
            this.bodyMessage = resDto.message;
            this.showModal = true;
          }
        });
    },

    modalOk() {
      this.showModal = false;
    },

  },

  components: {
    myModal
  },
}
</script>
