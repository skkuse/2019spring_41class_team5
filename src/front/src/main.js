// Import root libraries
import Vue from 'vue'
import Ionic from '@ionic/vue'
import Axios from 'axios'

// Import root components
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'

// Import ionic css
import '@ionic/core/css/core.css'
import '@ionic/core/css/ionic.bundle.css'
import './theme/variables.scss'

Vue.config.productionTip = false


// Library integration
Vue.prototype.$http = Axios
Ionic.install(Vue, {
})

// Global filters
Vue.filter('ordinalize', function (value) {
  if (!value) return ''
  let firstValue = value % 10
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
