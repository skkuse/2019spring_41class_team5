import Vue from 'vue'
import { IonicVueRouter } from '@ionic/vue'

Vue.use(IonicVueRouter)

export default new IonicVueRouter({
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('@/pages/RankPage.vue')
    },
    {
      path: '/item-detail/:id',
      component: () => import('@/pages/ItemDetailPage.vue')
    }
  ]
})
