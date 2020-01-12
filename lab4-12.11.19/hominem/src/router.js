import Vue from 'vue'
import VueRouter from "vue-router";
import Accessor from "@/components/Accessor";
import Validator from "@/components/Validator";
import NotFoundError from "@/components/Error";

Vue.use(VueRouter);

export default new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/login",
            name: "auth-page",
            component: Accessor
        },
        {
            path: "/app",
            name: "app-page",
            component: Validator,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("jwt")) next();
                else next({
                    name: "error-page",
                    params: {
                        errorCode: "401",
                        errorMessage: "Доступ неавторизованным пользователям запрещён"
                    }
                });
            }
        },
        {
            path: "/",
            name: "default-page",
            beforeEnter: (to, from, next) => {
                (localStorage.getItem("jwt") !== null) ? next({name: "app-page"}) : next({path: "auth-page"});
            }
        },
        {
            path: "/*",
            name: "error-page",
            component: NotFoundError,
            props: {
                default: true,
                errorCode: "404",
                errorMessage: "Страница, которую вы запрашиваете, отсутствует"
            }
        }
    ]
});