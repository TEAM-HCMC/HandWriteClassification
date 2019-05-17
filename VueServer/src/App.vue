<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

ul {
  /* 리스트앞에 붙는 점 사라지게해줌 */
  list-style-type: none;
  padding: 0;
}

li {
  /* 리스트를 한줄로 만들어줌 */
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

.frame {
  width: 80%;
  margin: 0 auto;
}
</style>

<template>
<div id="app">
  <div class="frame">

    <Header></Header>
    <Navigation></Navigation>
    <Container></Container>
    <Footer></Footer>

  </div>
</div>
</template>

<script>
import Header from './components/Header.vue'
import Navigation from './components/Navigation.vue'
import Container from './components/Container.vue'
import Footer from './components/Footer.vue'

const axios = require("axios")
const baseUrl = require('./config/serverUrl.js');
const accountStatus = require('./utils/accountStatus.js');

import {mapGetters} from 'vuex'

export default {
  name: 'app',

  computed: mapGetters({
    getEmail: 'getEmail',
    getName:'getName'
  }),

  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },

  components: {
    'Header': Header,
    'Navigation': Navigation,
    'Container': Container,
    'Footer': Footer
  }
}
</script>
