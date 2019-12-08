<template>
    <div id="content">
        <Notification :message="contentTitle" :is-error="false" :is-hidden="false"/>
        <hr/>
        <div id="wrapper">
            <div id="controlContainer">
                <table id="controlTable">
                    <tbody>
                    <tr>
                        <td colspan="4">
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
                        <td colspan="4">
                            <label>
                                Введите Y:
                                <input id="Y-input" required class="illuminated animated bordered rounded colored" type="text" placeholder="(-5 до 3)" maxlength="6" v-model="values.y">
                            </label>
                        </td>
                    </tr>
                    <tr><td colspan="4">Выберите R:</td></tr>
                    <tr>
                        <td><label>1<input type="radio" class="illuminated animated" value="1" v-model="values.r" v-on:click="redrawGraph"></label></td>
                        <td><label>2<input type="radio" class="illuminated animated" value="2" v-model="values.r" v-on:click="redrawGraph"></label></td>
                        <td><label>3<input type="radio" class="illuminated animated" value="3" v-model="values.r" v-on:click="redrawGraph"></label></td>
                        <td><label>4<input type="radio" class="illuminated animated" value="4" v-model="values.r" v-on:click="redrawGraph"></label></td>
                    </tr>
                    <tr><td colspan="3"><CheckButton :label="label" :action="validate"/></td></tr>
                    <tr>
                        <td><button v-on:click="deleteDots" title="Удалить все точки из базы данных и с графика" class="animated illuminated system-button">Удалить все точки</button></td>
                        <td><button v-on:click="logout" title="Завершить сеанс и вернуться на домашную страницу" class="animated illuminated system-button">Выйти</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="svgContainer">
                <svg id="graph" xmlns="http://www.w3.org/2000/svg" class="bordered rounded">
                    <line x1="0" y1="150" x2="300" y2="150" stroke="#000720"/>
                    <line x1="150" y1="0" x2="150" y2="300" stroke="#000720"/>
                    <polygon points="300,150 295,155 295, 145" fill="#000720" stroke="#000720"/>
                    <polygon points="150,0 145,5 155,5" fill="#000720" stroke="#000720"/>
                    <rect id="rect" x="30" y="90" width="120" height="60" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <polygon id="triangle" points="150,150 150,270 30,150" fill-opacity="0.4" stroke="navy" fill="blue"/>
                    <path id="path" d="M 150 150 L 270 150 C 270 210 210 270 150 270 Z" fill-opacity="0.4" stroke="navy" fill="blue"/>
                </svg>
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
                },
                dots: null
            }
        },
        methods: {
            validate: function () {
                if ((this.values.x !== null) && (this.values.y !== null) && (this.values.r !== null)) {
                    this.sendDot();
                    this.loadDots();
                    this.redrawGraph();
                } else {
                    this.tableNotification.message = "Не все поля заполнены";
                    this.tableNotification.isError = true;
                }
            },
            sendDot: function() {
                this.$axios.post("http://localhost:8080/add", {
                    user: localStorage.getItem("user"),
                    jwt: localStorage.getItem("jwt"),
                    x: this.values.x,
                    y: this.values.y,
                    r: this.values.r
                }).catch(error => {
                    this.tableNotification.message = `${error.response.status}: ${error.response.statusText}`;
                    this.tableNotification.isError = true;
                    this.tableNotification.isHidden = false;
                });
            },
            redrawGraph: function () {
                let r = this.values.r;
                const rect = document.getElementById("rect");
                const triangle = document.getElementById("triangle");
                const path = document.getElementById("path");
                rect.setAttribute("width", `${r * 24}`);
                rect.setAttribute("height", `${r * 12}`);
                rect.setAttribute("x", `${150 - r * 24}`);
                rect.setAttribute("y", `${150 - r * 12}`);
                triangle.setAttribute("points", `150,150 150,${150 - 24 * r} ${150 + 12 * r},150`);
                let k = (r !== 5) ? ((5 - r) * 12) : 0;
                path.setAttribute("d", `M 150 150 L ${150 + r * 24} 150 C ${150 + r * 24} ${210 - k} ${210 - k} ${150 + r * 24} 150 ${150 + r * 24} Z`);
                //TODO рисовать точки: если === null, то не рисовать
            },
            loadDots: function () {
                this.$axios.post("http://localhost:8080/load", {
                    user: localStorage.getItem("user"),
                    jwt: localStorage.getItem("jwt")
                }).then(response => {
                    this.dots = JSON.stringify(response.data.dots);
                }).catch(error => {
                    this.tableNotification.message = `${error.response.status}: ${error.response.statusText}`;
                    this.tableNotification.isError = true;
                    this.tableNotification.isHidden = false;
                });
            },
            deleteDots: function () {
                this.$axios.post("http://localhost:8080/clear", {
                    user: localStorage.getItem("user"),
                    jwt: localStorage.getItem("jwt")
                }).then(response => {
                    this.tableNotification.message = JSON.stringify(response.data.operationResult);
                    this.tableNotification.isHidden = false;
                    this.tableNotification.isError = false;
                }).catch(error => {
                    this.tableNotification.message = `${error.response.status}: ${error.response.statusText}`;
                    this.notificationParams.isError = true;
                    this.tableNotification.isHidden = false;
                });
            },
            logout: function () {
                this.$router.push({path: "/login"}, () => localStorage.clear());
            },
        },
        created: function () {
            const svg = document.getElementById("graph");
            //TODO отправка по нажатию на граф
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

    #svgContainer * {margin: 2px}

    .system-button {background-color: aliceblue}

    #controlTable * {margin: 4%}

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