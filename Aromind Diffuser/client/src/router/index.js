import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/Login'
import Register from '@/components/Register'
import Profile from '@/components/Profile'
import Feed from '@/components/Feed.vue'
import ProductCreate from '@/components/product/Create.vue'
import ProductEdit from '@/components/product/Edit.vue'
import ProductView from '@/components/product/View.vue'
import Community from '@/components/Community.vue'
import ArticleCreate from '@/components/article/Create.vue'
import ArticleEdit from '@/components/article/Edit.vue'
import ArticleView from '@/components/article/View.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path: "/shop",
      component: Feed
    },
    {
      path: "/products/create",
      component: ProductCreate
    },
    {
      path: "/products/:product/edit",
      component: ProductEdit
    },
    {
      path: "/products/:product",
      component: ProductView
    },
    {
      path: "/community",
      component: Community
    },
    {
      path: "/articles/create",
      component: ArticleCreate
    },
    {
      path: "/articles/:customcard_board/edit",
      component: ArticleEdit
    },
    {
      path: "/articles/:customcard_board",
      component: ArticleView
    }
  ]
})
