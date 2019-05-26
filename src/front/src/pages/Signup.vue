<!--
    Component: Signup
    Objective: Signup page
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [ ] Signup to app
    Changelog: 
-->
<template>
  <ion-content class="ion-content" padding color="primary">
    <ion-img src="/img/logo.png" />
    <div padding>
      <h1 text-center>Sign Up</h1>
      <ion-input
        placeholder="ID"
        :value="id"
        @ionChange="id = $event.target.value"
      />
      <ion-input
        placeholder="Name"
        :value="name"
        @ionChange="name = $event.target.value"
      />
      <ion-input
        placeholder="Organization"
        :value="organization"
        @ionChange="organization = $event.target.value"
      />
      <ion-input
        placeholder="Password"
        type="password"
        :value="password"
        @ionChange="password = $event.target.value"
      />
      <div>
        <ion-input
          placeholder="Confirm Password"
          type="password"
          debounce="1000"
          @ionChange="checkPassword($event.target.value)"
        />
      </div>
      <ion-button expand="block" color="light" @click="signup()">
        Sign up
      </ion-button>
      <ion-button expand="block" color="light" fill="clear" @click="back()">
        Cancel
      </ion-button>
    </div>
  </ion-content>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      name: "",
      organization: "",
      password: "",
      image: ""
    };
  },
  methods: {
    checkId(id) {},
    checkPassword(password) {
      if (this.password !== password)
        this.$action.toast("Password is not matched");
    },
    back() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
