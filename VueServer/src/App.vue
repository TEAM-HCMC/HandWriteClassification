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

</style>

<template>

<div id="app">

    <Header></Header>
    <Navigation></Navigation>
    <Container></Container>
    <Footer></Footer>

</div>

</template>

<script>

import Header from './components/Header.vue'
import Navigation from './components/Navigation.vue'
import Container from './components/Container.vue'
import Footer from './components/Footer.vue'

const axios = require("axios")
const baseUrl = require('./config/serverUrl.js');




export default {
    name: 'app',
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
