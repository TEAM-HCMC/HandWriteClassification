<style lang="css" scoped>



</style>

<template lang="html">

<Status></Status>

</template>

<script>

import PreLogin from './status/PreLogin.vue'
import PostLogin from './status/PostLogin.vue'

import {
  mapGetters
} from 'vuex'

const cookieUtils = require('./../../../utils/cookie');
const jwt = cookieUtils.getJwt();

var status = PreLogin;
if (jwt !== null) {
  status = PostLogin;
}

export default {
  computed: mapGetters({
    getEmail: 'getEmail',
    getName: 'getName'
  }),

  components: {
    'Status': status
  },
}
</script>
