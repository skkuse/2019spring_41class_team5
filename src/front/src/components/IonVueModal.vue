<template>
  <div>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="end">
          <ion-button @click="close()">Close</ion-button>
        </ion-buttons>
        <ion-title>{{ modalTitle }}</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content padding>
      <slot></slot>
    </ion-content>
  </div>
</template>

<script>
export default {
  name: "ionVueModal",
  props: ["modalTitle"],
  methods: {
    close() {
      this.$ionic.modalController.dismiss();
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
