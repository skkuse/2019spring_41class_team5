<template>
  <ion-vue-page page-title="Rank" :show-back="false">
    <div slot="toolbar-end">
      <ion-select
        interface="popover"
        :value="selectedCategory"
        @ionChange="selectedCategory = $event.target.value"
      >
        <ion-select-option
          v-for="(category, index) in categories"
          :key="category.index"
          :value="index"
        >{{ category.name }}</ion-select-option>
      </ion-select>
    </div>
    <div>
      <ion-card v-for="item in items" :key="item.rank" @click="handleItemClick(item)">
        <ion-card-header>
          <ion-grid no-padding>
            <ion-row>
              <ion-col size="8" no-padding>
                <ion-card-subtitle>{{ item.company }}</ion-card-subtitle>
                <ion-card-title>{{ item.name }}</ion-card-title>
              </ion-col>
              <ion-col size="4" no-padding text-right>
                <span class="rank">{{ item.rank | ordinalize }}</span>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-card-header>
        <ion-card-content class="item-content">
          <ion-grid no-padding>
            <ion-row>
              <ion-col size="6">
                <img :src="item.image | base64('jpg')">
              </ion-col>
              <ion-col size="6" no-padding text-right>
                <ul class="item-keyword" no-padding>
                  <li
                    v-for="itemKeyword in item.keywords"
                    :key="itemKeyword.rating"
                  >{{ itemKeyword.keyword.name }}</li>
                </ul>
                <div class="item-rating">
                  {{ item.rating.toFixed(1) }}
                  <small>/ 5</small>
                </div>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-card-content>
      </ion-card>
    </div>
  </ion-vue-page>
</template>

<script>
export default {
  name: "rank-page",
  data() {
    return {
      items: [],
      categories: [],
      selectedCategory: null
    };
  },
  methods: {
    handleItemClick(item) {
      console.log(item);
      this.$router.push({
        path: `ranking/item-detail/${item.id}`
      });
    },
    getItems(category) {
      return new Promise((onSuccess, onFailure) => {
        this.$http
          .get(`/items?itemCategoryId=${category.id}`)
          .then(response => {
            this.items = response.data
              .sort((a, b) => {
                return a.rating < b.rating ? 1 : a.rating > b.rating ? -1 : 0;
              })
              .map((item, index) => {
                item.rank = index + 1;
                item.keywords = item.keywords
                  .sort((a, b) => {
                    return a.rating < b.rating ? 1 : a.rating > b.rating ? -1 : 0;
                  })
                  .slice(0, 3);
                return item;
              });
            onSuccess();
          })
          .catch(error => {
            this.$action.toast(error.message);
            onFailure();
          });
      });
    },
    getCategories() {
      return new Promise((onSuccess, onFailure) => {
        this.$http
          .get(`/item-categories`)
          .then(response => {
            this.categories = response.data;
            onSuccess();
          })
          .catch(error => {
            this.$action.toast(error.message);
            onFailure();
          });
      });
    }
  },
  mounted() {
    this.getCategories().then(() => {
      this.selectedCategory = 0;
    });
  },
  watch: {
    selectedCategory(index) {
      this.getItems(this.categories[index]);
    }
  }
};
</script>

<style lang="scss" scoped>
.rank {
  font-size: large;
  font-weight: bold;
}
.item-keyword {
  list-style: none;
  display: flex;
  flex-direction: column-reverse;
  margin: 0;
}
.item-keyword li:nth-child(1) {
  color: var(--ion-color-primary);
  font-weight: bold;
}
.item-keyword li:nth-child(2) {
  font-size: small;
  color: var(--ion-color-primary);
}
.item-keyword li:nth-child(3) {
  font-size: smaller;
}
.item-rating {
  font-weight: bold;
  font-size: xx-large;
}
</style>
