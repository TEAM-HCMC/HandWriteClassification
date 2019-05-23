<style lang="css" scoped>

.sign {
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

    <div class="modal">
        <modal v-if="showModal" v-on:click="modalOk">
            <h3 slot="header">경고</h3>
            <span slot="body">로그인 후 이용할 수 있습니다.</span>
            <span slot="footer" v-on:click="modalOk">
        로그인 하십시오.
        <i class="closeModalBtn fas fa-times" aria-hidden="true"></i>
      </span>
        </modal>
    </div>
</div>

</template>

<script>
import modal from '../../../../utils/Modal.vue'

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
      this.$store.dispatch('login', this.accountLoginReqDto)
        .then((resDto) => {
          if(resDto.status===201){
            location.reload();
          }else{
            this.bodyMessage=resDto.message;
            this.showModal=true;
          }
        });
    },

    modalOk() {
      this.showModal = false;
    },

  },

  components: {
    modal
  },
}
</script>
