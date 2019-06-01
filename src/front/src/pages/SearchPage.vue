<!--
    Component: Search
    Objective: Search Items and show search history
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] Search Items
               - [x] Show Search History
               - [ ] Move to item if click each item
    Changelog: 
-->
<template>
  <ion-page class="ion-page">
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-back-button/>
        </ion-buttons>
        <ion-title>Search</ion-title>
      </ion-toolbar>
      <ion-toolbar>
        <ion-searchbar animated></ion-searchbar>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content" padding>
      <ion-list>
        <ion-list-header>
          <ion-label>Search History</ion-label>
        </ion-list-header>
        <ion-item v-for="(history, index) in histories" :key="index">
          <ion-label>{{ history.item.name }}</ion-label>
          <ion-button fill="clear" @click="deleteHistory(history.id)">
            <ion-icon name="close-circle"/>
          </ion-button>
        </ion-item>
        <ion-button fill="clear" @click="deleteHistoryAll">
          <ion-icon name="trash"></ion-icon>
          <ion-label>Delete All</ion-label>
        </ion-button>
      </ion-list>
    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "search-page",
  data() {
    return {
      histories: [
        {
          id: 1,
          item: {
            id: 1234,
            name: "HP Omen 15t"
          }
        },
        {
          id: 2,
          item: {
            id: 1234,
            name: "HP Omen 15t"
          }
        },
        {
          id: 3,
          item: {
            id: 1234,
            name: "HP Omen 15t"
          }
        },
        {
          id: 4,
          item: {
            id: 1234,
            name: "HP Omen 15t"
          }
        }
      ]
    };
  },
  methods: {
    deleteHistory(historyId) {
      const user = this.$store.state.user;
      this.$http.delete(`users/${user.id}/histories/${historyId}`).then(
        () => {
          this.histories = this.histories.filter(
            history => history.id !== historyId
          );
          this.$action.toast("Successfully deleted history.");
        },
        () => {
          this.$action.toast("Failed to delete history.");
        }
      );
    },
    deleteHistoryAll() {
      const user = this.$store.state.user;
      this.$http.delete(`users/${user.id}/histories`).then(
        () => {
          this.histories = [];
          this.$action.toast("Successfully deleted all history.");
        },
        () => {
          this.$action.toast("Failed to delete all history.");
        }
      );
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
