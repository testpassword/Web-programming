<template>
    <div id="header" class="shaded animated">
        <h1>{{ title }}</h1>
        <table id="credit">
            <tbody><tr>
                <td><img v-bind:src="authorLogoPath" alt="Аватар разработчика"></td>
                <td><a class="illuminated" v-bind:href="authorUrl" title="Перейти на сайт разработчика">{{ authorName }}</a></td>
            </tr></tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Header",
        props: {
            title: String,
            authorName: String,
            authorUrl: String,
            authorLogoPath: String
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