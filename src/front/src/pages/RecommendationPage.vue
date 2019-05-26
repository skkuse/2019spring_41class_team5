<template>
  <ion-page class="ion-page" main>
    <ion-content class="ion-content" padding>
      <header>
        <div>Electronics > Laptop</div>
        <h1>Recommendation</h1>
      </header>
      <section>
        <ion-card v-for="category in categories" :key="category.id"> </ion-card>
      </section>
      <footer text-center>
        <ion-button fill="clear">make your own priority</ion-button>
      </footer>
    </ion-content>
  </ion-page>
</template>

<script>
export default {
  name: "recommendation-page",
  data() {
    return {
      categories: [{}]
    };
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
</style>
