<template>
<ion-page class="ion-page">
    <ion-header>
        <ion-toolbar>
            <ion-buttons slot="start" v-show="">
                <ion-back-button default-href="" @click="back()" />
            </ion-buttons>
            <ion-title>{{ pageTitle }}</ion-title>
            <ion-buttons slot="end">
                <slot name="button-end">
                </slot>
            </ion-buttons>
        </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content">
        <slot></slot>
    </ion-content>
</ion-page>
</template>

<script>
export default {
    name: "ion-vue-page",
    props: ["pageTitle", "back"],
    data() {
        return {

        }
    },
    methods: {
        back() {
            this.$router.back();
        }
    }
}
</script>

<style>

</style>