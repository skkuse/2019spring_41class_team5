<template>
  <ion-app>
    <ion-vue-router></ion-vue-router>
  </ion-app>
</template>

<script>
export default {
  name: "app",
  mounted() {
    // Local Authentication Information Load
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));
    if (token && user) {
      this.$store.dispatch("preLogined", { token, user })
      .then();
    }
  }
};
</script>