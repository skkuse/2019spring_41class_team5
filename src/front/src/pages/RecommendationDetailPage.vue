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
        <ion-chip color="primary" v-for="(tag, index) in recommendation.tags" :key="index">
          <ion-icon name="pricetag"/>
          <ion-label>{{ tag }}</ion-label>
        </ion-chip>
      </div>
    </section>
    <section>
      <h1 margin-start>Our Recommendation</h1>
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
                <img src="/img/item-sample.jpg">
              </ion-col>
              <ion-col size="6" no-padding text-right>
                <ul class="item-keyword" no-padding>
                  <li v-for="(keyword, index) in item.keywords" :key="index">{{ keyword }}</li>
                </ul>
                <div class="item-score">
                  {{ item.score }}
                  <small>/ 5</small>
                </div>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-card-content>
      </ion-card>
    </section>
  </ion-vue-page>
</template>

<script>
export default {
  name: "recommendation-detail-page",
  data() {
    return {
      recommendation: {
        id: 1,
        name: "Graphic Design",
        description: "Laptop Description",
        thumbnail: "/img/thumbnail-sample.jpg",
        tags: ["asdf", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer"]
      },
      items: [
        {
          id: 1234,
          rank: 1,
          name: "Omet 16t",
          company: "Heulett-packard",
          keywords: ["Performance", "Extreme Gaming", "Build Quality"],
          image: "/img/item-sample.jpg",
          score: 4.7
        }
      ]
    };
  },
  methods: {
    handleItemClick(item) {
      this.$router.push({
        path: `recommendation/item-detail/${item.id}`
      });
    }
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
.item-score {
  font-weight: bold;
  font-size: xx-large;
}
</style>
