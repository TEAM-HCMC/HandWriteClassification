import Home from '../components/containers/mains/Home.vue'
import Service from '../components/containers/mains/Service.vue'
import About from '../components/containers/mains/About.vue'

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
  }
];
