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

export default {
  computed: mapGetters({
    getEmail: 'getEmail',
    getName: 'getName'
  }),

  data(){
    return {
      email :this.getEmail,
      name :this.getName
    }
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
