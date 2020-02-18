// Import root libraries
import Vue from 'vue'
import Ionic from '@ionic/vue'
import Moment from 'vue-moment'
import Axios from 'axios'
import Action from './util/action'
import Base64 from './util/base64'
import VeeValidate from 'vee-validate'

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

// Import global components
import IonVuePage from './components/IonVuePage.vue'
import IonVueModal from './components/IonVueModal.vue'
import TextHighlight from 'vue-text-highlight'
import VueCheckView from 'vue-check-view'

Vue.config.productionTip = false

// Library integration
Vue.use(Moment)
Vue.use(Action)
Vue.use(Base64)
Vue.use(Ionic)
Vue.use(VeeValidate)
Vue.use(VueCheckView)

// Global component integration
Vue.component('ion-vue-page', IonVuePage)
Vue.component('ion-vue-modal', IonVueModal)
Vue.component('text-highlight', TextHighlight)

// HTTP config
Vue.prototype.$http = Axios.create({
  baseURL: '/api'
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
  return ((value / maxValue) * 100).toFixed(1)
})
Vue.filter('base64', function(value, extension) {
  return `data:image/${extension};base64, ${value}`
})

// Start Vue App
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')