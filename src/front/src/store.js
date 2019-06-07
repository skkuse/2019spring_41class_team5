import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: () => ({
    token: null,
    user: null
  }),
  mutations: {
    login(state, payload) {
      this.state.token = payload.token;
      this.state.user = payload.user;
    },
    logout() {
      this.state.token = null;
      this.state.user = null;
    }
  },
  actions: {
    preLogined(context, params) {
      return new Promise((onSuccess, onFailure) => {
        if (!params.id || !params.password) {
          onFailure();
          return;
        }

        this.commit("login", params);
        Vue.prototype.$http.defaults.headers.common["Authorization"] =
          "Bearer " + params.token;
        onSuccess();
      });
    },
    login(context, params) {
      return new Promise((onSuccess, onFailure) => {
        if (!params.id || !params.password) {
          onFailure();
          return;
        }

        Vue.prototype.$http
          .post("/auth/sign-in", {
            id: params.id,
            password: params.password
          })
          .then(response => {
            this.commit("login", {
              token: response.data.token,
              user: response.data.user
            });
            Vue.prototype.$http.defaults.headers.common["Authorization"] =
              "Bearer " + response.data.token;

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("user", JSON.stringify(response.data.user));
            onSuccess(response);
          }, onFailure);
      });
    },
    logout() {
      this.commit("logout");
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      Vue.prototype.$http.defaults.headers.common["Authorization"] = undefined;
    }
  }
});
