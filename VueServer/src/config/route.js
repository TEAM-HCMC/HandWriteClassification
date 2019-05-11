import Home from '../components/containers/mains/Home.vue'
import Service from '../components/containers/mains/Service.vue'
import About from '../components/containers/mains/About.vue'
import PreLogin from '../components/containers/sides/status/PreLogin.vue'
import PostLogin from '../components/containers/sides/status/PostLogin.vue'

export const routes = [{
    path: '/',
    component: Home
  },
  {
    path: '/service',
    component: Service
  },
  {
    path: '/about',
    component: About
  },
];
