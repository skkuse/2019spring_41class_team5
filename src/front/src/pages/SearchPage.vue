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
        <ion-searchbar
          animated
          placeholder="Search.."
          :value="keyword"
          @ionFocus="handleInputFocus"
          @ionInput="keyword = $event.target.value"
          @keyup.enter="searchItems(keyword)"
        ></ion-searchbar>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content" padding>
      <ion-list v-if="!resultMode">
        <ion-list-header>
          <ion-label>Search History</ion-label>
        </ion-list-header>
        <ion-item v-for="(history, index) in histories" :key="index">
          <ion-label @click="searchItems(history.keyword)">{{ history.keyword }}</ion-label>
          <ion-button fill="clear" @click="deleteHistory(index)">
            <ion-icon name="close-circle"/>
          </ion-button>
        </ion-item>
        <ion-button fill="clear" @click="deleteHistoryAll">
          <ion-icon name="trash"></ion-icon>
          <ion-label>Delete All</ion-label>
        </ion-button>
      </ion-list>
      <ion-card v-for="(item, index) in items" :key="index">

      </ion-card>
    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "search-page",
  data() {
    return {
      keyword: null,
      resultMode: false,
      items: []
    };
  },
  methods: {
    handleInputFocus(){
      this.resultMode = false;
      this.keyword = null;
    },
    searchItems(keyword) {

      this.$store.dispatch("insertSearchHistory", {
        keyword: keyword,
        date: this.$moment()
      });

      this.resultMode = true;
    },
    deleteHistory(index) {
      this.$store.dispatch("deleteSearchHistory", index);
    },
    deleteHistoryAll() {
      this.$store.dispatch("deleteSearchHistoryAll");
    }
  },
  computed: {
    histories() {
      return this.$store.state.searchHistories;
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
