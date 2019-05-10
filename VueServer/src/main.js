import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueRouter from 'vue-router'


Vue.use(VueRouter);

import {routes} from './config/route'

const router = new VueRouter({
	routes
});

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
