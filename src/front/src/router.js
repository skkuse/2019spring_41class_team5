import Vue from 'vue'
import { IonicVueRouter } from '@ionic/vue'
import store from './store.js'

Vue.use(IonicVueRouter)

const router = new IonicVueRouter({
  base: process.env.BASE_URL,
  mode: 'history',
  routes: [
    {
      path: '/',
      component: () => import('@/components/TabView.vue'),
      meta: { permissions: ['user', 'admin'] },
      children: [
        {
          path: '/ranking',
          components: { ranking: () => import('@/pages/RankPage.vue') },
          meta: { permissions: ['user', 'admin'] },
        },
        {
          path: '/ranking/item-detail/:id',
          components: { ranking: () => import('@/pages/ItemDetailPage.vue') },
          meta: { permissions: ['user', 'admin'] },
        },
        {
          path: '/mypage',
          components: { mypage: () => import('@/pages/MyPage.vue') },
          meta: { permissions: ['user', 'admin'] },
        },
        {
          path: '/recommendation',
          components: { recommendation: () => import('@/pages/RecommendationPage.vue') },
          meta: { permissions: ['user', 'admin'] },
        },
      ]
    },
    {
      path: '/login',
      component: () => import('@/pages/Login.vue'),
      meta: { permissions: ['all'] }
    },
    {
      path: '/signup',
      component: () => import('@/pages/Signup.vue'),
      meta: { permissions: ['all'] }
    }
  ]
})

// Authorization
router.beforeEach((to, from, next) => {
  to.matched.forEach((entry) => {
    const permissions = entry.meta.permissions
    if (permissions.includes('user')) {
      if (store.state.token) next()
      else {
        to.path = '/login'
        next()
      }
    }
    else if (permissions.includes('admin')) {
      if (store.state.token && store.state.user.authority === 'admin') next()
      else {
        to.path = from.path

      }
    }
    else next()
  })
})

export default router