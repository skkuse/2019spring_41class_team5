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
      const token = localStorage.token;
      const user = localStorage.user;
      const searchHistories = localStorage.searchHistories;

    if (token && user) {
      this.$store.dispatch("preLogined", { token, user: JSON.parse(user) });
      this.$store.dispatch("setSearchHistories", JSON.parse(searchHistories));
    }

    // Authorization
    this.$router.beforeResolve((to, from, next) => {
      to.matched.forEach(entry => {
        const permissions = entry.meta.permissions;

        if (permissions.includes("user")) {
          if (this.$store.state.token) next();
          else {
            next("/login");
          }
        } else if (permissions.includes("admin")) {
          if (
            this.$store.state.token &&
            this.$store.state.user.authority === "admin"
          )
            next();
          else {
            next(false);
          }
        } else next();
      });
    });
  }
};
</script>