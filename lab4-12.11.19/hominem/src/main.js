import Vue from 'vue'
import App from './App.vue'
import router from "@/router";

Vue.config.productionTip = false;

new Vue({
  router, render: h => h(App),
}).$mount('#app');

/*
TODO скрывать бары при скроллинге;
TODO кнопка очистки графика от точек
TODO цвет true/false в таблице
TODO анимация при клике на график
 */