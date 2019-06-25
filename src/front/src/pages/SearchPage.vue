<!--
    Component: Search
    Objective: Search Items and show search history
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] Search Items
               - [x] Show Search History
               - [x] Move to item if click each item
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
          placeholder="Search"
          :value="keyword"
          @ionFocus="handleInputFocus"
          @ionBlur="handleInputBlur"
          @ionInput="keyword = $event.target.value"
          @keyup.enter="searchItems(keyword)"
        ></ion-searchbar>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content">
      <ion-list v-if="!resultMode" padding>
        <ion-list-header>
          <ion-label>Search History</ion-label>
        </ion-list-header>
        <ion-item v-for="(history, index) in histories" :key="index" @click="searchItems(history.keyword)">
          <ion-label>{{ history.keyword }}</ion-label>
          <ion-button fill="clear" @click.stop="deleteHistory(index)">
            <ion-icon name="close-circle"/>
          </ion-button>
        </ion-item>
        <ion-button fill="clear" @click="deleteHistoryAll">
          <ion-icon name="trash"></ion-icon>
          <ion-label>Delete All</ion-label>
        </ion-button>
      </ion-list>
      <div id="result" v-if="resultMode">
        <ion-card v-for="(item, index) in items" :key="index" @click="handleItemClick(item)">
          <ion-card-header>
            <ion-card-subtitle pull-right>
              <ion-img :src="item.image | base64('jpg')"></ion-img>
            </ion-card-subtitle>
            <ion-card-title>{{ item.name }}</ion-card-title>
            <ion-card-subtitle>{{ item.company }}</ion-card-subtitle>
            <span>{{ item.category.name }}</span>
          </ion-card-header>
        </ion-card>
      </div>
      <div id="no-result" text-center v-if="resultMode && items.length == 0">
        <h1>😐</h1>
        <span>NO RESULT</span>
      </div>
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
    handleItemClick(item) {
      this.$router.push({
        path: `/search/item-detail/${item.id}`
      });
    },
    handleInputFocus() {
      this.keyword = null;
    },
    handleInputBlur() {
      if(!this.keyword.length)
        this.resultMode = false;
      else this.resultMode = true;
    },
    searchItems(keyword) {
      this.resultMode = true;

      this.$store.dispatch("insertSearchHistory", {
        keyword: keyword,
        date: this.$moment()
      });

      this.$http.get(`/items?itemName=${keyword}`).then(response => {
        this.items = response.data;
      });
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
#no-result {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding-top: -3em;
  align-items: center;
  justify-content: center;
}

#no-result h1 {
  margin: 0;
  font-size: 50pt;
}

#result ion-card-title {
  font-size: 1.2em;
}
#result ion-card ion-img {
  height: 6em;
  width: 6em;
}
</style>
