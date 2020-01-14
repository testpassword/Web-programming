<template>
    <div id="content">
        <Notification message="Валидация введёных значений" :is-error="false" :is-visible="true"/>
        <hr/>
        <div id="wrapper">
            <div id="controlContainer">
                <table id="controlTable">
                    <tbody>
                    <tr>
                        <td colspan="4">
                            <label> Выберите X:
                                <select required class="animated illuminated bordered" v-model="point.x">
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
                                <input id="Y-input" required class="illuminated animated bordered rounded colored" type="text" placeholder="(-5 до 3)" maxlength="6" v-model="point.y">
                            </label>
                        </td>
                    </tr>
                    <tr><td colspan="4">Выберите R:</td></tr>
                    <tr>
                        <td><label>1<input type="radio" class="illuminated animated" value="1" v-model="point.r" @change="redrawGraph"></label></td>
                        <td><label>2<input type="radio" class="illuminated animated" value="2" v-model="point.r" @change="redrawGraph"></label></td>
                        <td><label>3<input type="radio" class="illuminated animated" value="3" v-model="point.r" @change="redrawGraph"></label></td>
                        <td><label>4<input type="radio" class="illuminated animated" value="4" v-model="point.r" @change="redrawGraph"></label></td>
                    </tr>
                    <tr><td colspan="3"><CheckButton color="red" label="Проверить" @click.native="validateFromButton"/></td></tr>
                    <tr>
                        <td><button @click="deletePoints" title="Удалить все точки из базы данных и с графика" class="animated illuminated system-button">Удалить все точки</button></td>
                        <td><button @click="logout" title="Завершить сеанс и вернуться на домашную страницу" class="animated illuminated system-button">Выйти</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="svgContainer">
                <svg id="graph" xmlns="http://www.w3.org/2000/svg" class="bordered rounded" @click="validateFromGraph">
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
            <Notification message="Точки отсутствуют" :is-error="false" :is-visible="points.length === 0"/>
            <Notification v-bind="errorTableNotification"/>
            <table v-if="points.length !== 0" id="outputTable">
                <thead>
                <tr>
                    <th>x</th>
                    <th>y</th>
                    <th>r</th>
                    <th>Точка входит в ОДЗ</th>
                    <th>Дата рождения</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="point in points" :key="point">
                    <td>{{ point.x }}</td>
                    <td>{{ point.y }}</td>
                    <td>{{ point.r }}</td>
                    <td>{{ (point.status) ? "\u2611" : "\u2610" }}</td>
                    <td>{{ new Date(Date.parse(point.bornDate)).toLocaleString() }}</td>
                </tr>
                </tbody>
            </table>
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
                errorTableNotification: {
                    message: undefined,
                    isError: true,
                    isVisible: false,
                },
                point: {
                    x: null,
                    y: null,
                    r: 4 //Изначально задано макс. значение для отрисовки графика в макс. масштабе
                },
                points: new Array(0)
            }
        },
        methods: {
            validateFromButton: function () {
                if ((this.point.x >= -4 && this.point.x <= 4) && this.point.y > -5 && this.point.y < 3) this.addPoint();
                else {
                    this.errorTableNotification.message = "Данные введены некорректно";
                    this.errorTableNotification.isVisible = true;
                }
            },
            validateFromGraph: function () {
                let position = getMousePosition(document.getElementById("graph"), event);
                this.point.x = ((position.x - 150) / 125 * this.point.r).toFixed(6);
                this.point.y = ((150 - position.y) / 125 * this.point.r).toFixed(6);
                this.addPoint();

                function getMousePosition(element, event) {
                    let rect = element.getBoundingClientRect();
                    return {
                        x: event.clientX - rect.left,
                        y: event.clientY - rect.top
                    };
                }
            },
            addPoint: function() {
                this.$axios.put("point", {
                    x: this.point.x,
                    y: this.point.y,
                    r: this.point.r
                }, {
                    headers: {Authorization: "Bearer " + localStorage.getItem("jwt")}
                }).then(() => {
                    this.loadPoints();
                }).catch(error => {
                    let answer = error.response.data.errors[0];
                    this.errorTableNotification.message = `${answer.field} ${answer.defaultMessage}`;
                    this.errorTableNotification.isVisible = true;
                });
            },
            loadPoints: function () {
                this.$axios.get("point", {
                    headers: {Authorization: "Bearer " + localStorage.getItem("jwt")}
                }).then(response => {
                    this.points = response.data;
                    this.redrawGraph();
                    this.errorTableNotification.isVisible = false;
                }).catch(error => {
                    this.errorTableNotification.message = error.response.statusText;
                    this.errorTableNotification.isVisible = true;
                });
            },
            redrawGraph: function () {
                let r = this.point.r;
                const svg = document.getElementById("graph");
                const rect = svg.getElementById("rect");
                const triangle = svg.getElementById("triangle");
                const path = svg.getElementById("path");
                rect.setAttribute("width", `${r * 30}`);
                rect.setAttribute("height", `${r * 15}`);
                rect.setAttribute("x", `${150 - r * 30}`);
                rect.setAttribute("y", `${150 - r * 15}`);
                triangle.setAttribute("points", `150,150 150,${150 + 30 * r} ${150 - 30 * r},150`);
                let k = (r !== 4) ? ((4 - r) * 14) : 0;
                path.setAttribute("d", `M 150 150 L ${150 + r * 30} 150 C ${150 + r * 30} ${210 - k} ${210 - k} ${150 + r * 30} 150 ${150 + r * 30} Z`);
                let oldPoints = document.querySelectorAll("circle");
                oldPoints.forEach(oldPoint => oldPoint.parentNode.removeChild(oldPoint));
                if (this.points.length !== 0) {
                    this.points.forEach(item => {
                        let newPoint = document.createElementNS("http://www.w3.org/2000/svg", "circle");
                        newPoint.setAttribute("r", "5");
                        newPoint.setAttribute("cx", `${item.x / r * 125 + 150}`);
                        newPoint.setAttribute("cy", `${150 - item.y / r * 125}`);
                        newPoint.setAttribute("fill", item.status === true ? "green" : "red");
                        svg.appendChild(newPoint);
                    });
                }
            },
            deletePoints: function () {
                this.$axios.delete("point", {
                    headers: {Authorization: "Bearer " + localStorage.getItem("jwt")}
                }).then(() => {
                    this.loadPoints();
                }).catch(error => {
                    this.errorTableNotification.message = error.response.statusText;
                    this.errorTableNotification.isVisible = true;
                });
            },
            logout: function () {
                this.$router.push({name: "auth-page"}, () => localStorage.clear());
            }
        },
        mounted: function () {
            this.loadPoints();
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