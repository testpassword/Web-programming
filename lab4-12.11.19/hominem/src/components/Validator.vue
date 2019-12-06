<template>
    <div id="content">
        <Notification :message="contentTitle" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="wrapper">
            <div id="controlContainer">
                <table id="controlTable">
                    <tbody>
                    <tr>
                        <td colspan="3">
                            <label> Выберите X:
                                <select required class="animated illuminated bordered" v-model="values.x">
                                    <option>-4</option>
                                    <option>-3</option>
                                    <option>-2</option>
                                    <option>-1</option>
                                    <option>0</option>
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <label>
                                Введите Y:
                                <input id="Y-input" required class="illuminated animated bordered rounded" type="text" placeholder="(-5 до 3)" maxlength="6" v-model="values.y">
                            </label>
                        </td>
                    </tr>
                    <tr><td colspan="3">Выберите R:</td></tr>
                    <tr>
                        <td><label>-4<input type="radio" class="illuminated animated" value="-4" v-model="values.r"></label></td>
                        <td><label>-3<input type="radio" class="illuminated animated" value="-3" v-model="values.r"></label></td>
                        <td><label>-2<input type="radio" class="illuminated animated" value="-2" v-model="values.r"></label></td>
                    </tr>
                    <tr>
                        <td><label>-1<input type="radio" class="illuminated animated" value="-1" v-model="values.r"></label></td>
                        <td><label>0<input type="radio" class="illuminated animated" value="0" v-model="values.r"></label></td>
                        <td><label>1<input type="radio" class="illuminated animated" value="1" v-model="values.r"></label></td>
                    </tr>
                    <tr>
                        <td><label>2<input type="radio" class="illuminated animated" value="2" v-model="values.r"></label></td>
                        <td><label>3<input type="radio" class="illuminated animated" value="3" v-model="values.r"></label></td>
                        <td><label>4<input type="radio" class="illuminated animated" value="4" v-model="values.r"></label></td>
                    </tr>
                    <tr><td colspan="5"><CheckButton :label="label" :action="validate"/></td></tr>
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
                    <polygon id="triangle" points="150,150 150,270 30,150" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <path id="circle" d="M 150 150 L 270 150 C 270 210 210 270 150 270 Z" fill-opacity="0.4" stroke="navy" fill="blue"/>
                </svg>
                <button v-on:click="logout" title="Завершить сеанс и вернуться на домашную страницу" type="button">Выйти</button>
                <p><a href="" title="Завершить сеанс и вернуться на домашную страницу">Выйти</a></p>
            </div>
        </div>
        <hr/>
        <div id="outputContainer">
            <Notification v-bind="tableNotification"/>
        </div>
    </div>
</template>

<script>
    import CheckButton from "@/components/CheckButton";
    import Notification from "@/components/Notification";

    export default {
        name: "Validator",
        components: {Notification, CheckButton},
        data: function () {
            return {
                contentTitle: "Валидация введёных значений",
                label: "Проверить",
                tableNotification: {
                    message: "Результаты отсутствуют",
                    isError: false,
                    isHidden: false,
                },
                values: {
                    x: null,
                    y: null,
                    r: null
                }
            }
        },
        methods: {
            validate: function () {
                if ((this.values.x !== null) && (this.values.y !== null) && (this.values.r !== null)) {
                    alert("норм");
                } else alert("не норм");
            },
            logout: function () {
                this.router.push({path: "/login"}, () => localStorage.clear());
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