<!--
    Component: ItemDetailPage
    Objective: Show specific item detail
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] View item spec
               - [x] View item review summary
               - [x] View item review details
               - [ ] Add/Edit/Delete user review
               - [ ] Dynamic loading for review lists
    Changelog: 
-->
<template>
  <ion-vue-page page-title="Item Detail" show-back="true">
    <section id="item-detail" margin>
      <ion-grid>
        <ion-row>
          <ion-col size="6">
            <ion-img :src="item.image | base64('jpg')"/>
          </ion-col>
          <ion-col size="6">
            <div id="item-header">
              <h1>{{ item.name }}</h1>
              <span>{{ item.company }}</span>
            </div>
            <ul id="item-specs">
              <li v-for="(spec, index) in item.specs" :key="index">{{ spec }}</li>
            </ul>
          </ion-col>
        </ion-row>
        <ion-row align-items-center>
          <ion-col size="6">
            <ul id="item-vendors">
              <li v-for="(vendorLink, index) in item.vendorLinks" :key="index">
                <a :href="vendorLink.vendor.itemDetailUrl">{{ vendorLink.vendor.name }}</a>
              </li>
            </ul>
          </ion-col>
          <ion-col size="6" text-right>
            <ion-button size="small" fill="outline" @click="addItemToBookmarks" v-if="!bookmark">
              <ion-icon name="bookmark"/>
              <span>Bookmark</span>
            </ion-button>
            <ion-button size="small" fill="solid" @click="removeItemFromBookmarks" v-if="bookmark">
              <ion-icon name="bookmark"/>
              <span>Bookmarked</span>
            </ion-button>
          </ion-col>
        </ion-row>
      </ion-grid>
    </section>
    <section id="review-analysis" margin>
      <h1>Review Analysis</h1>
      <ion-grid>
        <ion-row>
          <ion-col id="review-score" align-items-end text-center>
            <h2>{{ item.rating | percent(5) }}%</h2>
            <span>recommended</span>
          </ion-col>
        </ion-row>
        <ion-row id="review-keywords">
          <h2>Most mentioned keywords</h2>
          <ion-col size="6">
            <ul id="review-keywords-positive" class="review-keywords">
              <li v-for="(iKeyword, index) in positiveKeywords" :key="index">{{ iKeyword.keyword.name }}</li>
            </ul>
          </ion-col>
          <ion-col size="6" text-right>
            <ul id="review-keywords-negative" class="review-keywords">
              <li v-for="(iKeyword, index) in negativeKeywords" :key="index">{{ iKeyword.keyword.name }}</li>
            </ul>
          </ion-col>
        </ion-row>
      </ion-grid>
    </section>
    <section id="review-detail">
      <h1 margin-start>Item Reviews</h1>
      <ion-card v-for="review in reviews" :key="review.id">
        <ion-card-header>
          <ion-card-subtitle pull-right>importance: {{ review.importance | percent(5) }}%</ion-card-subtitle>
          <ion-card-subtitle>{{ review.authorName }}</ion-card-subtitle>
          <ion-card-title>{{ review.title }}</ion-card-title>
          <span v-if="review.rating > 3.5">positive</span>
          <span v-else-if="review.rating > 2.0">neutral</span>
          <span v-else>negative</span>
        </ion-card-header>
        <ion-card-content>{{ review.content }}</ion-card-content>
      </ion-card>
    </section>
  </ion-vue-page>
</template>

<script>
export default {
  name: "item-detail-page",
  data() {
    return {
      item: {},
      bookmark: null,
      reviews: null
    };
  },
  methods: {
    getItem() {
      return new Promise((onSuccess, onFailure) => {
        this.$http.get(`/items/${this.$route.params.id}`).then(result => {
          this.item = result.data;
          onSuccess();
        });
      });
    },
    getReviews() {
      return new Promise((onSuccess, onFailure) => {
        this.$http.get(`/reviews?itemId=${this.item.id}`).then(result => {
          this.reviews = result.data;
          onSuccess();
        });
      });
    },
    getBookmark() {
      return new Promise((onSuccess, onFailure) => {
        const criteria = {
          itemId: this.$route.params.id,
          userId: this.$store.state.user
        };

        this.$http.get("/bookmarks", { params: criteria }).then(result => {
          this.bookmark = result.data ? result.data[0] : null;
          onSuccess();
        });
      });
    },
    addItemToBookmarks() {
      const bookmark = {
        user: this.$store.state.user,
        item: this.item
      };

      this.$http.post(`/bookmarks`, bookmark).then(response => {
        this.bookmark = response.data;
        this.$action.toast("Added to bookmarks!");
      });
    },
    removeItemFromBookmarks() {
      this.$http.delete(`/bookmarks/${this.bookmark.id}`).then(response => {
        this.bookmark = null;
        this.$action.toast("Removed from bookmarks.");
      });
    }
  },
  created() {
    this.getItem()
      .then(this.getBookmark)
      .then(this.getReviews);
  },
  computed: {
    positiveKeywords() {
      if(!Object.keys(this.item).length) return [];
      return this.item.keywords
        .filter(keyword => keyword.count > 0)
        .filter(keyword => keyword.rating > 2.5)
        .sort((a, b) => {
          return a.rating < b.rating ? 1 : a.rating > b.rating ? -1 : 0;
        });
    },
    negativeKeywords() {
      if(!Object.keys(this.item).length) return [];
      return this.item.keywords
        .filter(keyword => keyword.count > 0)
        .filter(keyword => keyword.rating < 2.5)
        .sort((a, b) => {
          return a.rating < b.rating ? 1 : a.rating > b.rating ? -1 : 0;
        });
    }
  }
};
</script>

<style lang="scss" scoped>
/* Page Global Sytles */
section h1 {
  font-size: 1.5em;
  font-weight: bold;
}

/* Item Details Section */

#item-header h1 {
  font-size: 1.5em;
  font-weight: bold;
  margin: 0;
}

#item-header span {
  font-size: 0.8em;
  color: var(--ion-color-primary);
}

#item-specs {
  margin-top: 0.5em;
  padding: 0;
}

#item-specs li {
  list-style: none;
  font-size: 0.7em;
  color: var(--ion-color-dark);
}

#item-specs li::before {
  content: "- ";
}

#item-vendors {
  margin: 0;
}

#item-vendors li {
  display: inline;
  list-style: none;
  font-size: 0.8em;
}

#item-vendors li:not(:first-of-type)::before {
  content: " | ";
  color: var(--ion-color-dark);
}

/* Review Analysis Section */

#review-score h2 {
  font-size: 4em;
  font-weight: bolder;
  margin-bottom: 0;
}

#review-keywords h2 {
  font-size: 1em;
  font-weight: bold;
}

.review-keywords li:nth-child(1) {
  font-weight: bold;
  font-size: 1.3em;
}

.review-keywords li:nth-child(2) {
  font-weight: bold;
  font-size: 1.1em;
}

.review-keywords li:nth-child(3) {
  font-size: 1em;
}

.review-keywords li {
  font-size: 0.8em;
}

#review-keywords-positive li {
  color: var(--ion-color-primary);
}

#review-keywords-negative li {
  color: var(--ion-color-dark-tint);
}
</style>
