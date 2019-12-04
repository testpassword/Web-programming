<template>
    <div id="content">
        <Notification :message="contentTitle" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="wrapper">
            <div id="controlContainer">
                <table id="controlTable">
                    <tbody>
                    <tr><td colspan="5">Выберите X:</td></tr>
                    <tr>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="-2.0"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="-1.5"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="-1.0"></td>
                    </tr>
                    <tr>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="-0.5"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="0"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="0.5"></td>
                    </tr>
                    <tr>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="1"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="1.5"></td>
                        <td><input name="X-button" class="illuminated animated rounded" type="button" value="2"></td>
                    </tr>
                    <tr><td colspan="5"><label for="Y-input">Введите Y:</label></td></tr>
                    <tr><td colspan="5"><input id="Y-input" required name="Y-input" class="illuminated animated rounded" type="text" placeholder="(-5 до 3)" maxlength="6"></td></tr>
                    <tr><td colspan="5">Выберите R:</td></tr>
                    <tr>
                        <td><label>1<input name="R-radio-group" class="illuminated animated rounded" type="radio" value="1"></label></td>
                        <td><label>2<input name="R-radio-group" class="illuminated animated rounded" type="radio" value="2"></label></td>
                        <td><label>3<input name="R-radio-group" class="illuminated animated rounded" type="radio" value="3"></label></td>
                    </tr>
                    <tr><td colspan="5"><CheckButton :label="label" :action="action"/></td></tr>
                    </tbody>
                </table>
            </div>
            <div id="svgContainer">
                <svg id="graph" xmlns="http://www.w3.org/2000/svg" class="rounded">
                    <line x1="0" y1="150" x2="300" y2="150" stroke="#000720"/>
                    <line x1="150" y1="0" x2="150" y2="300" stroke="#000720"/>
                    <polygon points="300,150 295,155 295, 145" fill="#000720" stroke="#000720"/>
                    <polygon points="150,0 145,5 155,5" fill="#000720" stroke="#000720"/>
                    <rect id="rect" x="30" y="90" width="120" height="60" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <polygon id="triangle" points="150,150 150,30 210,150" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <path id="circle" d="M 150 150 L 270 150 C 270 210 210 270 150 270 Z" fill-opacity="0.4" stroke="navy" fill="blue"/>
                </svg>
                <p><a href="" title="Завершить сеанс и вернуться на домашную страницу">Выйти</a></p>
            </div>
        </div>
        <hr/>
        <div id="outputContainer">
            <Notification :message="message" :is-error="isError" :is-hidden="isHidden"/>
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
                isHidden: false,
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

    #wrapper {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    #wrapper * {
        min-width: 50%;
    }

    #outputContainer {
        margin-bottom: 130px;
    }

    input, svg {
        background-color: white;
    }

    svg {
        width: 300px;
        height: 300px;
        box-shadow: inset 0 0 7px 1px gray;
    }

    #controlTable * {margin: 2%}

    input[name=X-button] {width: 100px}

    input[name=R-radio-group] {width: 10px}

    /*#outputTable {
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