import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: () => ({
    token: null,
    user: null,
    searchHistories: []
  }),
  mutations: {
    login(state, payload) {
      this.state.token = payload.token;
      this.state.user = payload.user;
    },
    logout() {
      this.state.token = null;
      this.state.user = null;
    },
    insertSearchHistory(state, history) {
      this.state.searchHistories.push(history);
    },
    deleteSearchHistory(state, index) {
      this.state.searchHistories.splice(index, 1);
    },
    deleteSearchHistoryAll(state) {
      this.state.searchHistories = [];
    },
    setSearchHistories(context, histories) {
      this.state.searchHistories = histories;
    }
  },
  actions: {
    preLogined(context, params) {
      return new Promise((onSuccess, onFailure) => {
        if (!params.token || !params.user) {
          onFailure();
          return;
        }
        Vue.prototype.$http.defaults.headers.common["Authorization"] =
          "Bearer " + params.token;

        Vue.prototype.$http.get("/").then(
          () => {
            this.commit("login", params);
            onSuccess();
          },
          () => {
            this.commit("logout");
            localStorage.clear();
            Vue.prototype.$http.defaults.headers.common["Authorization"] = undefined;
            onFailure();
          });
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

            localStorage.token = response.data.token;
            localStorage.user = JSON.stringify(response.data.user);
            onSuccess(response);
          }, onFailure);
      });
    },
    logout() {
      this.commit("logout");
      localStorage.clear();
      Vue.prototype.$http.defaults.headers.common["Authorization"] = undefined;
    },
    setSearchHistories(context, histories) {
      this.commit('setSearchHistories', histories);
    },
    insertSearchHistory(context, history) {
      this.commit('insertSearchHistory', history);
      localStorage.setItem('searchHistories', JSON.stringify(this.state.searchHistories));
    },
    deleteSearchHistory(context, historyIndex) {
      this.commit('deleteSearchHistory', historyIndex);
      localStorage.setItem('searchHistories', JSON.stringify(this.state.searchHistories));
    },
    deleteSearchHistoryAll(context) {
      this.commit('deleteSearchHistoryAll');
      localStorage.removeItem('searchHistories');
    }
  }
});