import Vue from 'vue'
import Ionic from '@ionic/vue'

// Local Config Import
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'

// CSS Import
import '@ionic/core/css/core.css'
import '@ionic/core/css/ionic.bundle.css'
import './theme/variables.scss'

Vue.config.productionTip = false

// Ionic Integration
Vue.use(Ionic)
Ionic.for

// Global Filters
Vue.filter('ordinalize', function (value) {
  if (!value) return ''
  let firstValue = value % 10
  if (firstValue === 1) return value + 'st'
  if (firstValue === 2) return value + 'nd'
  if (firstValue === 3) return value + 'rd'
  else return value + "th"
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
