<template>
    <div id="content">
        <Notification :message="'(' + time + ') ' + message" :is-error="isError"/>
        <hr/>
        <table>
            <tr>
                <td><label for="loginInput">Введите почту:</label></td>
                <td><input id="loginInput" class="illuminated animated rounded colored" type="text" placeholder="email"/></td>
            </tr>
            <tr>
                <td><label for="passwordInput">Введите пароль:</label></td>
                <td><input id="passwordInput" class="illuminated animated rounded colored" type="password" placeholder="secret_word"/></td>
            </tr>
        </table>
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
        position: relative;
        top: 30%;
    }

    #content * {margin: 2%}

    table {
        width: 100%
    }
</style>