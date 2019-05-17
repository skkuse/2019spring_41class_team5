<template>
  <ion-page class="ion-page">
    <ion-header>
      <ion-toolbar>
        <ion-title>Item Detail - {{ title }}</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content">

    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "item-detail-page",
  data() {
    return {
        title: this.$route.params.id
    };
  }
};
</script>

<style lang="scss" scoped>
</style>
