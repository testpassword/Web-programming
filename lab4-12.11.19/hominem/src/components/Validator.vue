<template>
    <div id="content" class="shaded">
        <div id="headContainer">
            <h3 id="title">{{ contentTitle }}</h3>
            <hr/>
        </div>
        <div id="appContainer">
            <a href="" title="Вернуться на домашную страницу">Домой</a> <!--ссылка-->
            <CheckButton/>
            <hr/>
        </div>
        <div id="outputContainer">
            <h4><span class="outputStub notification">Результаты отсутствуют</span></h4>
        </div>
    </div>
</template>

<script>
    import CheckButton from "@/components/CheckButton";

    export default {
        name: "Content",
        components: {CheckButton},
        props: ["contentTitle"]
    }
</script>

<style scoped>

    body {background-color: whitesmoke}

    #content {
        background-color: ghostwhite;
        width: 80%;
        margin: 100px auto;
    }

    hr {
        color: #f41c52;
        width: 95%;
    }

    .notification {
        color: white;
        padding: 10px;
    }

    .outputStub {background-color: #000720}

    .errorStub {background-color: #dc143b}

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