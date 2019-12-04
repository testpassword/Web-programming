<template>
    <button @click="action">{{ label }}</button>
</template>

<script>
    export default {
        name: "CheckButton",
        props: {
            label: String,
            action: Function,
        }
    }
</script>

<style scoped>
    /* Взято и переработано с https://codepen.io/rauldronca/pen/mARYEm*/
    button {
        text-align: center;
        text-transform: uppercase;
        cursor: pointer;
        letter-spacing: 2px;
        position: relative;
        background-color: #eb2a5a;
        border: none;
        color: white;
        padding: 15px;
        width: 150px;
        transition-duration: 0.4s;
        overflow: hidden;
        box-shadow: 0 5px 15px #193047;
        border-radius: 4px;
    }

    button:hover {
        background: white;
        box-shadow: 0 2px 10px 5px #eb2a5a;
        color: black;
    }

    button::after {
        content: "";
        background: #eb2a5a;
        display: block;
        position: absolute;
        padding-top: 300%;
        padding-left: 350%;
        margin-left: -20px !important;
        margin-top: -120%;
        opacity: 0;
        transition: all 0.8s
    }

    button:active::after {
        padding: 0;
        margin: 0;
        opacity: 1;
        transition: 0s
    }

    button:focus {outline: 0}
</style>