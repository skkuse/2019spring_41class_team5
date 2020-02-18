<!--
    Component: Signup
    Objective: Signup page
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [ ] Signup to app
    Changelog: 
-->
<template>
  <ion-content class="ion-content" padding color="primary">
    <ion-img src="/img/logo.png"/>
    <div padding>
      <h1 text-center>Sign Up</h1>
      <ion-input
        placeholder="ID"
        :value="form.id"
        @ionChange="form.id = $event.target.value; idChecked = false"
        @ionBlur="checkId($event.target.value)"
        :color="idErrorColor"
      />
      <ion-input
        placeholder="Name"
        :value="form.name"
        @ionChange="form.name = $event.target.value"
      />
      <ion-input
        placeholder="Organization"
        :value="form.organization"
        @ionChange="form.organization = $event.target.value"
      />
      <ion-input
        placeholder="Password"
        type="password"
        :value="form.password"
        @ionChange="form.password = $event.target.value"
      />
      <div>
        <ion-input
          placeholder="Confirm Password"
          type="password"
          debounce="500"
          @ionChange="checkPassword($event.target.value); passwordConfirmation = $event.target.value"
          :color="passwordErrorColor"
        />
        <ion-text color="danger" v-if="passwordError">
          <small>password is not match</small>
        </ion-text>
      </div>
      <ion-button expand="block" color="light" @click="signup()">Sign up</ion-button>
      <ion-button expand="block" color="light" fill="clear" @click="back()">Cancel</ion-button>
    </div>
  </ion-content>
</template>

<script>
export default {
  data() {
    return {
      form: {
        id: "",
        name: "",
        organization: "",
        password: "",
        image: ""
      },
      passwordConfirmation: "",
      idChecked: false,
      idError: false,
      passwordError: false
    };
  },
  methods: {
    checkId(id) {
      this.$http.get(`/auth/exists/${id}`).then(response => {
        if (response.data == true) {
          this.$action.toast("This id is already exists. choose another one.");
          this.idError = true;
          this.idChecked = true;
        } else {
          this.idError = false;
          this.idChecked = true;
        }
      });
    },
    checkPassword(password) {
      if (this.form.password !== password) {
        this.passwordError = true;
      } else {
        this.passwordError = false;
      }
    },
    signup() {
      if (
        !this.form.id ||
        !this.form.name ||
        !this.form.organization ||
        !this.form.password ||
        !this.passwordConfirmation
      ) {
        this.$action.toast("You have some missing input. Please check again.");
        return;
      }
      if (this.passwordError || !this.idChecked || this.idError) {
        this.$action.toast("You have error in form data. Please check again.");
        return;
      }
      this.$http.post("/auth/sign-up", this.form).then(response => {
        this.$action.toast("Success!");
        this.$router.replace("/login");
      });
    },
    back() {
      this.$router.go(-1);
    }
  },
  computed: {
    idErrorColor() {
      return this.idError ? "danger" : "light";
    },
    passwordErrorColor() {
      return this.passwordError ? "danger" : "light";
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
