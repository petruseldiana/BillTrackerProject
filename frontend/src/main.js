import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import Vue from 'vue';
import App from './App.vue';
import router from './router/index';
import store from './store/index';
import BootstrapVue from 'bootstrap-vue';
import VeeValidate from 'vee-validate';
import 'vue-awesome/icons';
import Icon from 'vue-awesome/components/Icon';

Vue.component('v-icon', Icon)

Vue.config.productionTip = false

// Bootstrap
Vue.use(BootstrapVue)
Vue.use(VeeValidate, {
    inject: true,
    fieldsBagName: 'veeFields'
  });

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');

