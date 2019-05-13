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
const accountStatus = require('../../../../utils/accountStatus');

export default {
  data(){
    return {
      email : localStorage.getItem('email'),
      name : localStorage.getItem('name')
    }
  },

  methods: {
      logout() {
          accountStatus.logout();
      }
  }
}
</script>

<style lang="css" scoped>
</style>
