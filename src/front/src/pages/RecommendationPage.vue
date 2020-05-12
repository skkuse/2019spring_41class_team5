<template>
  <ion-page class="ion-page" main>
    <ion-header>
      <ion-toolbar>
        <ion-buttons>
          <ion-back-button/>
        </ion-buttons>
        <ion-title>Recommendation</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content">
      <section v-for="category in categories" :key="category.id" padding-top>
        <h2 no-margin margin-start>{{ category.name }}</h2>
        <div scroll-x>
          <ion-card
            v-for="recommendation in recommendations[''+category.id]"
            :key="recommendation.id"
            @click="handleRecommendationDetailButtonClick(recommendation)"
          >
            <ion-img :src="recommendation.thumbnail"/>
            <ion-card-header>
              <ion-card-title>{{ recommendation.name }}</ion-card-title>
            </ion-card-header>
          </ion-card>
        </div>
      </section>
    </ion-content>
    <ion-footer>
      <footer text-center>
        <ion-button
          fill="clear"
          @click="handleNewRecommendationButtonClick()"
        >make your own priority</ion-button>
      </footer>
    </ion-footer>
  </ion-page>
</template>

<script>
import Vue from 'vue'
import NewRecommendationModal from "../components/NewRecommendationModal.vue";
export default {
  name: "recommendation-page",
  data() {
    return {
      categories: [],
      recommendations: {}
    };
  },
  methods: {
    handleNewRecommendationButtonClick() {
      this.$ionic.modalController
        .create({
          component: NewRecommendationModal,
          componentProps: {
            propsData: {
              user: this.$store.state.user
            }
          }
        })
        .then(a => a.present());
    },
    handleRecommendationDetailButtonClick(recommendation) {
      this.$router.push({path: `/recommendation/detail/${recommendation.id}`});
    }
  },
  created() {
      this.$http.get(`/item-categories`).then(result => {
        this.categories = result.data;
        result.data.forEach(category => {
          this.$http
            .get(`/recommendations?itemCategoryId=${category.id}`)
            .then(result => {
              Vue.set(this.recommendations, category.id, result.data);
            });
        });
      });
  }
};
</script>

<style lang="scss" scoped>
ion-card-title {
  font-size: 1em;
}
ion-card {
  display: inline-block;
  width: 70%;
}
section ion-card:not(:last-child) {
  margin-right: 0;
}
</style>
