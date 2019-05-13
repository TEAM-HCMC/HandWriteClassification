<style lang="css" scoped>



</style>

<template lang="html">

<Status></Status>

</template>

<script>
import PreLogin from './status/PreLogin.vue'
import PostLogin from './status/PostLogin.vue'
var status = PreLogin;
if (localStorage.getItem('email') !== null) {
  status = PostLogin;
}

export default {
  
  components: {
    'Status': status
  },
}
</script>
