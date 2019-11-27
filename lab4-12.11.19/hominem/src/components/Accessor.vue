<template>
    <div id="content">
        <Notification id="title" :message="'(' + time + ') ' + message" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="controlContainer">
            <div id="login">
                <label for="loginInput">Введите почту:</label>
                <input id="loginInput" class="illuminated animated rounded colored" type="text" placeholder="email"/>
            </div>
            <div id="password">
                <label for="passwordInput">Введите пароль:</label>
                <input id="passwordInput" class="illuminated animated rounded colored" type="password" placeholder="secret_word"/>
            </div>
        </div>
        <CheckButton :label="label"/>
        <hr/>
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
                action: function () {
                    alert("Здесь будет вход");
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

<!--метка залезает на hr при изменении размера окна-->
