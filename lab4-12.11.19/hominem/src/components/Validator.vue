<template>
    <div id="content">
        <div id="headContainer">
            <Notification :message="contentTitle" :is-error="false"/>
            <hr/>
        </div>
        <div id="appContainer">
            <a href="" title="Вернуться на домашную страницу">Домой</a> <!--ссылка-->
            <CheckButton :label="label"/>
            <hr/>
        </div>
        <div id="outputContainer">
            <Notification :message="message" :is-error="isError"/>
        </div>
    </div>
</template>

<script>
    import CheckButton from "@/components/CheckButton";
    import Notification from "@/components/Notification";

    export default {
        name: "Content",
        components: {Notification, CheckButton},
        data: function () {
            return {
                contentTitle: "Валидация введёных значений",
                label: "Проверить",
                message: "Результаты отсутствуют",
                isError: false
            }
        }
    }
</script>

<style scoped>

    /*input, svg {
        width: 100%;
        border: 2px solid #f41c52;
        border-radius: 20px;
        background-color: white;
    }

    input[type=radio] {width: 50px}

    input, a {cursor: pointer}

    svg {
        width: 300px;
        height: 300px;
        box-shadow: inset 0 0 7px 1px gray;
    }

    #outputTable {
        border: 1px solid #000720;
        border-collapse: collapse;
        margin: auto;
        width: 90%;
    }

    #outputTable th {
        background-color: #000720;
        color: white;
    }

    #outputTable * {
        padding: 25px;
    }*/
</style>