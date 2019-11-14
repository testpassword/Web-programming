<template>
    <div id="header" class="shaded animated">
        <h1>{{ title }}</h1>
        <table id="credit">
            <tbody><tr>
                <td><img v-bind:src="authorLogo" alt="Аватар разработчика"></td>
                <td><a class="illuminated" v-bind:href="authorUrl" title="Перейти на сайт разработчика">{{ authorName }}</a></td>
            </tr></tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data: function () {
            return {
                title: "Веб-программирование, Лаб. 4, Вариант XXX",
                authorName: "Кульбако Артемий Юрьевич, P3212",
                authorUrl: "https://github.com/testpassword",
                authorLogo: "/assets/img/avatar.png"
            }
        },
        created: function () {
            let buttons = document.querySelectorAll("input");
            buttons.forEach(click);

            function click(element) {
                element.onclick = function () {
                    buttons.forEach(function (element) {
                        element.style.boxShadow = null;
                        element.style.backgroundColor = null;
                        element.style.color = null;
                    });
                    this.style.boxShadow = "0 0 40px 5px #f41c52";
                    this.style.backgroundColor = "#f41c52";
                    this.style.color = "white";
                }
            }
        }
    }
</script>

<style scoped>
    #header {
        font-size: 0.6em;
        top: 0;
        left: 0;
        z-index: 10; /*чтобы header всегда был сверху*/
        width: 100%;
        position: fixed;
        color: white;
        background-color: #000720;
    }

    #credit {margin: auto}

    #credit img {
        width: 40px;
        height: 40px;
    }

    #credit a {
        font-size: 15px;
        text-decoration: none;
        color: white;
    }
</style>