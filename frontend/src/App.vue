<script setup>
import HeaderView from "@/components/HeaderView.vue";
import FooterView from "@/components/FooterView.vue";
</script>

<template>
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Jua&display=swap"
            rel="stylesheet"
        />
        />
    </head>
    <HeaderView />
    <main>
        <RouterView />
    </main>
    <FooterView />
</template>

<style scoped></style>
