<template>
  <ion-vue-modal modal-title="New Recommendation">
    <ion-list lines="full">
      <ion-item>
        <ion-label position="stacked">Recommendation Name</ion-label>
        <ion-input @ionChange="recommendation.name = $event.target.value"/>
      </ion-item>
      <ion-item>
        <ion-label position="stacked">Description</ion-label>
        <ion-input @ionChange="recommendation.description = $event.target.value"/>
      </ion-item>
      <ion-item>
        <ion-label position="stacked">Category</ion-label>
        <ion-select
          interface="alert"
          :value="selectedCategory"
          @ionChange="selectedCategory = $event.target.value"
        >
          <ion-select-option
            v-for="(category, index) in categories"
            :key="category.id"
            :value="index"
          >{{ category.name }}</ion-select-option>
        </ion-select>
      </ion-item>
      <ion-item>
        <ion-label position="stacked">Thumbnail Image</ion-label>
        <ion-img :src="recommendation.thumbnail" v-if="recommendation.thumbnail"/>
        <ion-button shape="clear" @click="handleThumbnailUploadButtonClick()">Upload</ion-button>
        <input
          type="file"
          ref="inputThumbnailImage"
          accept="image/*"
          @change="changePreview"
          hidden
        >
      </ion-item>
      <ion-item>
        <ion-label position="stacked">Keywords</ion-label>
        <ion-select multiple @ionChange="selectedKeywords = $event.target.value">
          <ion-select-option
            v-for="(keyword, index) in keywords"
            :key="index"
            :value="index"
          >{{ keyword.name }}</ion-select-option>
        </ion-select>
      </ion-item>
    </ion-list>
    <ion-button expand="block" @click="handleUploadButtonClick()">
      <ion-icon name="upload"></ion-icon>Upload
    </ion-button>
  </ion-vue-modal>
</template>

<script>
export default {
  name: "new-recommendation-modal",
  data() {
    return {
      recommendation: {
        name: "",
        description: "",
        category: "",
        thumbnail: "",
        keywords: []
      },
      categories: [],
      keywords: [],
      selectedCategory: null,
      selectedKeywords: []
    };
  },
  methods: {
    handleThumbnailUploadButtonClick() {
      this.$refs.inputThumbnailImage.click();
    },
    handleUploadButtonClick() {
      if (
        !this.recommendation.name ||
        !this.recommendation.description ||
        !this.recommendation.category ||
        !this.recommendation.thumbnail ||
        !this.recommendation.keywords.length
      ) {
        this.$action.toast("You have some missing input. Please check again.");
        return;
      }
      this.$http.post(`/recommendations`, this.recommendation).then(() => {
        this.$action.toast("Added Recommendation!");
      });

      this.$ionic.modalController.dismiss();
    },
    changePreview(event) {
      const input = event.target;
      if (input.files && input.files[0]) {
        this.$base64.encode(input.files[0]).then(result => {
          this.recommendation.thumbnail = result;
        });
      }
    },
    getKeywords(categoryId) {
      this.$http
        .get(`/keywords?itemCategoryId=${categoryId}`)
        .then(response => {
          this.keywords = response.data;
        });
    }
  },
  created() {
    this.$http.get(`/item-categories`).then(response => {
      this.categories = response.data;
      this.selectedCategory = 0;
      this.recommendation.category = response.data[0];
    });
  },
  watch: {
    selectedCategory() {
      this.recommendation.category = this.categories[this.selectedCategory];
      this.getKeywords(this.recommendation.category.id);
    },
    selectedKeywords() {
      this.recommendation.keywords = this.selectedKeywords.map(
        keyword => this.keywords[keyword]
      );
    }
  }
};
</script>

<style lang="scss" scoped>
#item-profile ion-img {
  max-width: 50%;
}
</style>
