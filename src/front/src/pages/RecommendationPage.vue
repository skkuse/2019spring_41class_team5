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
      <section v-for="category in categories" :key="category.id">
        <h2 no-margin margin-start>{{ category.name }}</h2>
        <div scroll-x>
          <ion-card v-for="recommendation in category.recommendations" :key="recommendation.id"
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
import NewRecommendationModal from "../components/NewRecommendationModal.vue";
export default {
  name: "recommendation-page",
  data() {
    return {
      categories: [
        {
          id: 1,
          name: "Laptop",
          recommendations: [
            {
              id: 1,
              name: "for Graphic Design",
              description: "Laptop Description",
              thumbnail: "/img/thumbnail-sample.jpg",
              tags: ["asdf", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer"]
            },
            {
              id: 2,
              name: "for Graphic Design",
              description: "Laptop Description",
              thumbnail: "/img/thumbnail-sample.jpg",
              tags: ["asdf", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer"]
            },
            {
              id: 3,
              name: "for Graphic Design",
              description: "Laptop Description",
              thumbnail: "/img/thumbnail-sample.jpg",
              tags: ["asdf", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer", "qwer"]
            }
          ]
        }
      ]
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
      this.$router.push(`/recommendation/${recommendation.id}`);
    }
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
