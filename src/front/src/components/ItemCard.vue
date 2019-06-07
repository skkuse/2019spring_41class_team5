<template>
  <ion-card>
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
</template>

<script>
export default {
    name: "item-card",
    props: ["item"],
    methods: {

    }
}
</script>

<style lang="scss" scoped>
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
