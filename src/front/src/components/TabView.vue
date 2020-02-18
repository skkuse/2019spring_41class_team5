<template>
  <div>
    <ion-tabs>
      <ion-tab tab="ranking">
        <router-view name="ranking"></router-view>
      </ion-tab>
      <ion-tab tab="recommendation">
        <router-view name="recommendation"></router-view>
      </ion-tab>
      <ion-tab tab="mypage">
        <router-view name="mypage"></router-view>
      </ion-tab>
      <ion-tab tab="search">
        <router-view name="search"></router-view>
      </ion-tab>
      <router-view></router-view>
      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="ranking" to="/ranking">
          <ion-icon name="ribbon"></ion-icon>
          <ion-label>Ranking</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="recommendation" to="/recommendation">
          <ion-icon name="thumbs-up"></ion-icon>
          <ion-label>Recommendation</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="mypage" to="/mypage">
          <ion-icon name="person"></ion-icon>
          <ion-label>Mypage</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="search" to="/search">
          <ion-icon name="search"></ion-icon>
          <ion-label>Search</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>
  </div>
</template>

<script>
export default {
  name: "tab-view",
  mounted() {}
};
</script>

<style>
</style>