<style lang="css">

.side{

  margin-top: 2em;

}

</style>

<template lang="html">

<div class="side">
  <Status></Status>
</div>


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
