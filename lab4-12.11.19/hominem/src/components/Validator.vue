<template>
    <div id="content">
        <div id="headContainer">
            <Notification :message="contentTitle" :is-error="false"/>
        </div>
        <hr/>
        <div id="appContainer">
            <div id="controlContainer">
                <div id="xContainer>">
                    Выберите X:
                    <input name="X-button" class="illuminated animated" type="button" value="-2.0">
                    <input name="X-button" class="illuminated animated" type="button" value="-1.5">
                    <input name="X-button" class="illuminated animated" type="button" value="-1.0">
                    <input name="X-button" class="illuminated animated" type="button" value="-0.5">
                    <input name="X-button" class="illuminated animated" type="button" value="0">
                    <input name="X-button" class="illuminated animated" type="button" value="0.5">
                    <input name="X-button" class="illuminated animated" type="button" value="1">
                    <input name="X-button" class="illuminated animated" type="button" value="1.5">
                    <input name="X-button" class="illuminated animated" type="button" value="2">
                </div>
                <div id="yContainer">
                    Введите Y:
                    <input required name="Y-input" class="illuminated animated" type="text" placeholder="значение в промежутке (-5 до 3)" maxlength="6">
                </div>
                <div id="rContainer">
                    Выберите R:
                    <label>1<input name="R-radio-group" class="illuminated animated" type="radio" value="1"></label>
                    <label>2<input name="R-radio-group" class="illuminated animated" type="radio" value="2"></label>
                    <label>3<input name="R-radio-group" class="illuminated animated" type="radio" value="3"></label>
                    <label>4<input name="R-radio-group" class="illuminated animated" type="radio" value="4"></label>
                    <label>5<input name="R-radio-group" class="illuminated animated" type="radio" value="5"></label>
                </div>
                <CheckButton :label="label" :action="action"/>
            </div>
            <div id="resultContainer">
                <svg id="graph" xmlns="http://www.w3.org/2000/svg">
                    <line x1="0" y1="150" x2="300" y2="150" stroke="#000720"/>
                    <line x1="150" y1="0" x2="150" y2="300" stroke="#000720"/>
                    <polygon points="300,150 295,155 295, 145" fill="#000720" stroke="#000720"/>
                    <polygon points="150,0 145,5 155,5" fill="#000720" stroke="#000720"/>
                    <rect id="rect" x="30" y="90" width="120" height="60" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <polygon id="triangle" points="150,150 150,30 210,150" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <path id="circle" d="M 150 150 L 270 150 C 270 210 210 270 150 270 Z" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <circle id="target" r="5" cx="0" cy="0" fill-opacity="0.7" fill="red" visibility="hidden"/>
                </svg>
                <p><a href="" title="Вернуться на домашную страницу">Домой</a></p> &lt;!&ndash;ссылка&ndash;&gt;
            </div>
        </div>
        <hr/>
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
                isError: false,
                action: function () {
                    alert("Здесь будет валидация");
                }
            }
        }
    }
</script>

<style scoped>
    #content {
        margin-left: 5%;
        margin-right: 5%;
    }

    input, svg {
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
    }
</style>