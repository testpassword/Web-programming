<template>
    <div id="footer" class="shaded animated">
        <figure>
            <a v-bind:href="pulpitUrl">
                <img v-bind:src="pulpitLogo" alt="Лого сайта кафедры" title="Перейти на сайт кафедры" class="illuminated">
            </a>
            <figcaption>{{ devYear }}</figcaption>
        </figure>
    </div>
</template>

<script>
    export default {
        name: "Footer",
        props: ["pulpitLogo", "pulpitUrl", "devYear"]
    }
</script>

<style scoped>

    #footer {
        bottom: 0;
        z-index: 10; /*чтобы footer всегда был сверху*/
        width: 100%;
        position: fixed;
        color: white;
        background-color: #000720;
        left: 0;
    }

    figure img {
        width: 47px;
        height: 55px;
    }

    figcaption {
        font-size: 0.8em;
        display: block;
    }

    figure {padding: 0.5%}

</style>