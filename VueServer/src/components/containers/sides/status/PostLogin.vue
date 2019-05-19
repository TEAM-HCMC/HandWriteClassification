<template lang="html">

  <div class="login_form">

    <div class="account_id">
        ID : {{email}}

    </div>
    <div class="account_name">
        NAME : {{name}}
    </div>
    <button  v-on:click="logout">로그아웃</button>

  </div>

</template>

<script>
import {mapGetters} from 'vuex'
const httpAccount = require('../../../../http/account');

export default {
  computed: mapGetters({
    getEmail: 'getEmail',
    getName: 'getName'
  }),

  data(){
    return {
      email :null,
      name :null,
    }
  },

  created(){
      httpAccount.getAccountInfo().then((reqDto)=>{
        this.email = reqDto.email;
        this.name = reqDto.name;
      })
  },

  methods: {
      logout() {
          this.$store.commit('logout');
      }
  }
}
</script>

<style lang="css" scoped>
</style>
