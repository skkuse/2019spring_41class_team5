import 'onsenui/css/onsenui.css'
import 'onsenui/css/onsen-css-components.css'

import Vue from 'vue'
import $ons from 'vue-onsenui/esm'
import * as VOns from './vue-onsen-components'
import App from './App.vue'
import router from './router'
import store from './store'
import './registerServiceWorker'

Vue.config.productionTip = false

Vue.use($ons)
Object.values(VOns).forEach(comp => Vue.component(comp.name, comp))

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
