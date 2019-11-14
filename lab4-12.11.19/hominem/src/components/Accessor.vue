<template>
    <div id="content">
        <Notification :message="'(' + time + ') ' + message" :is-error="isError"/>
        <hr/>
        <label for="loginInput">Введите почту:</label><input id="loginInput" class="illuminated animated rounded" type="text" placeholder="email"/>
        <label for="passwordInput">Введите пароль:</label><input id="passwordInput" class="illuminated animated rounded" type="password" placeholder="secret_word"/>
        <hr/>
        <CheckButton :label="label"/>
    </div>
</template>

<script>
    import CheckButton from "@/components/CheckButton";
    import Notification from "@/components/Notification";

    export default {
        name: "Filter",
        components: {Notification, CheckButton},
        data: function () {
            return {
                label: "Войти",
                message: "Вход в систему",
                time: undefined,
                isError: false
            }
        },
        methods: {
          clock: function () {
              let date = new Date(),
                  hours = (date.getHours() < 10) ? "0" + date.getHours() : date.getHours(),
                  minutes = (date.getMinutes() < 10) ? "0" + date.getMinutes() : date.getMinutes(),
                  seconds = (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
              this.time = `${hours}:${minutes}:${seconds}`;
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

    #content * {margin: 2%}
</style>