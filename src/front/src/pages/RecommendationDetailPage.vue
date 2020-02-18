<!--
    Component: RecommendationDetailPage
    Objective: Show Recommendation Detail 
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] View recommendation items
               - [x] View recommendation tags
               - [x] Link to recommended item
    Changelog: 
-->
<template>
  <ion-vue-page page-title="Recommendation List">
    <section
      id="header"
      padding
      :style="{background: `linear-gradient(rgba(255, 255, 255, 0.8), rgba(0, 0, 0, 0.5)), url(${recommendation.thumbnail})`}"
    >
      <small>{{ recommendation.description }}</small>
      <h1>
        <small>for</small>
        {{ recommendation.name }}
      </h1>
    </section>
    <section padding-start padding-top>
      <div scroll-x>
        <ion-chip color="primary" v-for="(keyword, index) in recommendation.keywords" :key="index">
          <ion-icon name="pricetag"/>
          <ion-label>{{ keyword.name }}</ion-label>
        </ion-chip>
      </div>
    </section>
    <section>
      <h1 margin-start>Our Recommendation</h1>
      <ion-card v-for="item in items" :key="item.rating" @click="handleItemClick(item)">
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
      <div id="no-result" text-center v-if="items.length == 0">
        <h1>😐</h1>
        <span>NO RESULT</span>
      </div>
    </section>
  </ion-vue-page>
</template>

<script>
export default {
  name: "recommendation-detail-page",
  data() {
    return {
      recommendation: {},
      items: []
    };
  },
  methods: {
    handleItemClick(item) {
      this.$router.push({
        path: `recommendation/item-detail/${item.id}`
      });
    }
  },
  created() {
    this.$http.get(`/recommendations/${this.$route.params.id}`).then(result => {
      this.recommendation = result.data;
      this.$http
        .get(`/items?recommendationId=${this.recommendation.id}`)
        .then(result => {
          this.items = result.data
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
        });
    });
  }
};
</script>

<style lang="scss" scoped>
#header {
  height: 20%;
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

#header h1 {
  margin-top: 0.2em;
  margin-bottom: 0;
}

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

#no-result {
  height: 200px;
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
