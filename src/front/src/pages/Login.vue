<!--
    Component: Login
    Objective: Login page
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] Login to app
    Changelog: 
-->
<template>
  <ion-content class="ion-content" padding color="primary">
    <ion-img id="logo" src="/img/icons/android-chrome-192x192.png"/>
    <div padding>
      <ion-input placeholder="ID" :value="id" @ionChange="id = $event.target.value"/>
      <ion-input
        placeholder="Password"
        type="password"
        :value="password"
        @ionChange="password = $event.target.value"
      />
      <ion-button expand="block" color="light" @click="login()">Log In</ion-button>
      <div text-right>
        <router-link :to="{ path: 'signup' }">
          <ion-button fill="clear" size="small" color="light">Sign up</ion-button>
        </router-link>
      </div>
    </div>
  </ion-content>
</template>

<script>
export default {
  name: "login-page",
  data() {
    return {
      id: "",
      password: ""
    };
  },
  methods: {
    login() {
      this.$store
        .dispatch("login", { id: this.id, password: this.password })
        .then(
          response => {
            this.$action.toast("Successfully Logged In.");
            this.$router.push("/");
          },
          response => {
            this.$action.toast(
              "Failed to Login.\nPlease check your id or password."
            );
          }
        );
    }
  }
};
</script>

<style lang="scss" scoped>
#logo {
  margin: 25%;
}

ion-grid {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
</style>
