import Vue from 'vue'
import App from './App.vue'
import router from "@/router";
import Axios from "axios";

Vue.config.productionTip = false;
Vue.prototype.$axios = Axios.create({baseURL: "/api"});

new Vue({router, render: h => h(App)}).$mount('#app');