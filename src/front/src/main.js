// Import root libraries
import Vue from 'vue'
import Vuex from 'vuex'
import Ionic from '@ionic/vue'
import Moment from 'vue-moment'
import Axios from 'axios'
import Action from './util/action'

// Import root components
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'

// Import css
import '@ionic/core/css/core.css'
import '@ionic/core/css/ionic.bundle.css'
import './theme/variables.scss'
import './theme/global.scss'

Vue.config.productionTip = false

// Library integration
Vue.use(Moment)
Vue.use(Action)
Vue.use(Ionic)

// HTTP config
Vue.prototype.$http = Axios.create({
  baseURL: '/backend/'
});

// Global filters
Vue.filter('ordinalize', function (value) {
  if (!value) return ''
  const firstValue = value % 10
  if (firstValue === 1) return value + 'st'
  if (firstValue === 2) return value + 'nd'
  if (firstValue === 3) return value + 'rd'
  else return value + "th"
})
Vue.filter('percent', function (value, maxValue) {
  return (value / maxValue) * 100
})

// Start Vue App
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
