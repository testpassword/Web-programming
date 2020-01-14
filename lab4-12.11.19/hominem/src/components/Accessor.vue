<template>
    <div id="content">
        <Notification id="title" :message="`(${time}) Вход в систему`" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="controlContainer">
            <div id="login">
                <label for="loginInput">Введите почту:</label>
                <input id="loginInput" required class="illuminated animated bordered rounded colored" type="text" placeholder="email" v-model.trim="email"/>
            </div>
            <div id="password">
                <label for="passwordInput">Введите пароль:</label>
                <input id="passwordInput" required class="illuminated animated bordered rounded colored" type="password" placeholder="secret_word" v-model.trim="password"/>
            </div>
            <div>
                <CheckButton color="blue" label="Зарегистрироваться" @click.native="register"/>
                <CheckButton color="red" label="Войти" @click.native="login"/>
                <CheckButton color="yellow" label="Удалить" @click.native="remove"/>
            </div>
        </div>
        <hr/>
        <Notification v-bind="notificationParams"/>
    </div>
</template>

<script>
    import CheckButton from "@/components/CheckButton";
    import Notification from "@/components/Notification";

    export default {
        name: "Accessor",
        components: {Notification, CheckButton},
        data: function () {
            return {
                time: null,
                email: "",
                password: "",
                notificationParams: {
                    isVisible: false,
                    isError: true,
                    message: undefined
                }
            }
        },
        methods: {
            clock: function () {
                let date = new Date(),
                    hours = (date.getHours() < 10) ? "0" + date.getHours() : date.getHours(),
                    minutes = (date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes(),
                    seconds = (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
                this.time = `${hours}:${minutes}:${seconds}`;
            },
            login: function () {
                if (this.validateForm()) {
                    this.$axios.post("user", {
                        email: this.email,
                        password: this.password
                    }).then(response => {
                        localStorage.setItem("jwt", response.data);
                        this.$router.push({name: "app-page"});
                    }).catch(error => this.defaultAxiosErrorHandler(error));
                }
            },
            register: function () {
                if (this.validateForm()) {
                    this.$axios.put("user", {
                        email: this.email,
                        password: this.password
                    }).then((response) => {
                        this.notificationParams.message = response.data;
                        this.notificationParams.isError = false;
                        this.notificationParams.isVisible = true;
                        setTimeout(this.login, 1000);
                    }).catch(error => this.defaultAxiosErrorHandler(error));
                }
            },
            remove: function () {
                if (this.validateForm()) {
                    this.$axios.post("user/del", {
                        email: this.email,
                        password: this.password
                    }).then((response) => {
                        this.notificationParams.message = response.data;
                        this.notificationParams.isError = false;
                        this.notificationParams.isVisible = true;
                    }).catch(error => this.defaultAxiosErrorHandler(error))
                }
            },
            defaultAxiosErrorHandler: function (error) {
                this.notificationParams.message = error.response.data;
                this.notificationParams.isVisible = true;
                this.notificationParams.isError = true;
            },
            validateForm: function () {
                let regExp = new RegExp(/\S+@\S+\.\S+/);
                if (this.email.length === 0 || this.password.length === 0) {
                    this.notificationParams.isVisible = true;
                    this.notificationParams.isError = true;
                    this.notificationParams.message = "Данные не введены";
                    return false;
                } else if (!regExp.test(String(this.email).toLowerCase())) {
                    this.notificationParams.isError = true;
                    this.notificationParams.isVisible = true;
                    this.notificationParams.message = "email введён некорректно";
                    return false;
                } else return true;
            }
        },
        created: function () {
            setInterval(this.clock, 1000);
            this.clock();
        }
    }
</script>

<style scoped>
    #content {
        margin-left: 10%;
        margin-right: 10%;
    }

    #controlContainer * {margin: 1.5%}

    #controlContainer {
        display: flex;
        flex-direction: column;
    }
</style>