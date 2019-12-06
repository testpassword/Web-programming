import Vue from 'vue'
import VueRouter from "vue-router";
import Accessor from "@/components/Accessor";
import Validator from "@/components/Validator";
import NotFoundError from "@/components/NotFoundError";

Vue.use(VueRouter);

export default new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/login",
            component: Accessor
        },
        {
            path: "/app",
            component: Validator,
            /*beforeEnter: (to, from, next) => {
                if (localStorage.getItem("jwt") !== null) next();
                else {
                    alert("Доступ неавторизованным пользователям запрещён");
                    next({path: "/login"});
                }
            } мешает во время отладки*/
        },
        {
            path: "/*",
            component: NotFoundError
        }
    ]
});