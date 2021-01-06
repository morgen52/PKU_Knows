import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: ()=>import(/* webpackChunkName: "about" */ '../views/Login.vue'),
    //children:[{
                //path:'/signup',
                //name:'Signup',
                //component:()=>import(/* webpackChunkName: "about" */ '../views/Signup.vue')
            //}]
  },
  {
    path: '/signup',
    name: 'Signup',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Signup.vue')
  },
  {
    path: '/reset',
    name: 'Reset',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
	
    component: () => import(/* webpackChunkName: "about" */ '../views/Reset.vue')
  },
  {
    path: '/user',
    name: 'User',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/User.vue')
  },
  {
    path: '/cog',
    name: 'Cog',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Cog.vue')
  },  
  {
    path: '/homepage',
    name: 'Homepage',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import( '../views/Homepage.vue')
  },
  {
    path: '/qa',
    name: 'Qa',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import( '../views/Qa.vue')
  },
  {
    path: '/set',
    name: 'Set',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import( '../views/Set.vue')
  },
  {
		path: '/sso',
		name: 'Sso',
		component: () => import( '../views/Sso.vue')
  }
]
const router = new VueRouter({
  routes
})

export default router
