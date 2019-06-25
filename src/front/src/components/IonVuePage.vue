<template>
  <ion-page class="ion-page">
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start" v-if="showBack">
          <slot name="toolbar-start"></slot>
          <ion-back-button default-href @click="back()"/>
        </ion-buttons>
        <ion-title>{{ pageTitle }}</ion-title>
        <ion-buttons slot="end">
          <slot name="toolbar-end"></slot>
        </ion-buttons>
      </ion-toolbar>
      <slot name="toolbar-additional"></slot>
    </ion-header>
    <ion-content class="ion-content">
      <slot></slot>
    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "ion-vue-page",
  props: ["pageTitle", "showBack"],
  data() {
    return {
      prevRoute: null
    };
  },
  methods: {
    back() {
      if (this.prevRoute) this.$router.go(this.prevRoute.path);
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.prevRoute = from;
    });
  }
};
</script>

<style>
</style>