<template>
  <ion-page class="ion-page">
    <ion-header>
      <ion-toolbar>
        <ion-title>Rank</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content>
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
                <img src="/img/item-sample.jpg" />
              </ion-col>
              <ion-col size="6" no-padding text-right>
                <ul class="item-keyword" no-padding>
                  <li v-for="(keyword, index) in item.keywords" :key="index">
                    {{ keyword }}
                  </li>
                </ul>
                <div class="item-score">
                  {{ item.score }} <small>/ 5</small>
                </div>
              </ion-col>
            </ion-row>
          </ion-grid>
        </ion-card-content>
      </ion-card>
    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "rank-page",
  data() {
    return {
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
        path: `ranking/item-detail/${item.id}`
      })
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
.item-score {
  font-weight: bold;
  font-size: xx-large;
}
</style>
