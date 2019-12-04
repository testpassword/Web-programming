<template>
    <div id="content">
        <h1 id="404">404</h1>
        <h2>Ничего не найдено</h2>
        <p>Страница, которую вы запрашиваете, отсутствует, проверьте url-адрес.<br/>
            <router-link to="/login" class="illuminated">Вернуться на домашную страницу</router-link>
        </p>
    </div>
</template>

<script>
    export default {
        name: "404Error"
    }
</script>

<style scoped>
    h1 {
        font-size: 8em;
        margin: 0;
        background: linear-gradient(130deg, #2a48eb, #ff0018);
        color: transparent;
        background-clip: text;
        text-transform: uppercase;
    }

    h2 {
        font-size: 33px;
        font-weight: 200;
        text-transform: uppercase;
        margin: 0;
        letter-spacing: 3px;
    }
</style>