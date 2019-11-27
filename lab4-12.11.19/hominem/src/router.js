import Vue from 'vue'
import VueRouter from "vue-router";
import Accessor from "@/components/Accessor";
import Validator from "@/components/Validator";
import ClientError from "@/components/ClientError";

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
            component: Validator
        },
        {
            path: "/*",
            component: ClientError
        }
    ]
})