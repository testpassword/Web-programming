<template>
    <div id="content">
        <Notification id="title" :message="'(' + time + ') ' + message" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="controlContainer">
            <div id="login">
                <label for="loginInput">Введите почту:</label>
                <input id="loginInput" required class="illuminated animated rounded colored" type="text" placeholder="email" v-model.trim="email"/>
            </div>
            <div id="password">
                <label for="passwordInput">Введите пароль:</label>
                <input id="passwordInput" required class="illuminated animated rounded colored" type="password" placeholder="secret_word" v-model.trim="password"/>
            </div>
        </div>
        <CheckButton :label="label" :action="submitHandler"/>
        <hr/>
        <Notification v-bind="notificationParams"/>
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
                time: null,
                email: "",
                password: "",
                notificationParams: {
                    isHidden: true,
                    isError: true,
                    message: null
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
          submitHandler: function () {
              if (this.email.length > 0 && this.password.length > 0) {
                  this.$axios.post("http://localhost:8080/auth", {
                      email: this.email,
                      password: this.password
                  }).then(response => {
                      localStorage.setItem("user", JSON.stringify(response.data.user));
                      localStorage.setItem("jwt", response.data.token);
                  }).catch(error => {
                      this.notificationParams.message = error.response;
                      this.notificationParams.isHidden = false;
                  });
                  /*
                  1. Отправить данные
                  2. Получен ответ - установить данные или вывести инфу об отсутсвии пользователя
                  3. Если плохой код ответа - вывести это
                   */
              } else {
                  this.notificationParams.message = "Данные не введены";
                  this.notificationParams.isHidden = false;
              }
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