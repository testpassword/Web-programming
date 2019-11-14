<template>
    <h3><span class="notification stub" :class="{ errorStub: isError }">{{ message }}</span></h3>
</template>

<script>
    export default {
        name: "Notification",
        props: {
            isError: Boolean,
            message: String
        }
    }
</script>

<style scoped>
    .notification {
        color: white;
        padding: 10px;
    }

    .stub {background-color: #000720}

    .errorStub {background-color: #dc143b}
</style>