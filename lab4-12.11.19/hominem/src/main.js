import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";

Vue.use(VueRouter);
Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
}).$mount('#app');

/*
TODO скрывать бары при скроллинге;
TODO кнопка очистки графика от точек
TODO цвет true/false в таблице
TODO анимация при клике на график
 */