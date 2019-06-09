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
            :key="category.index"
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
        <ion-label position="stacked">Tags</ion-label>
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
        tags: []
      },
      categories: [],
      selectedCategory: null
    };
  },
  methods: {
    handleThumbnailUploadButtonClick() {
      this.$refs.inputThumbnailImage.click();
    },
    handleUploadButtonClick() {
      this.$http.post(`/recommendations`, recommendation).then(() => {
        this.$action.toast("Added Recommendation!");
      });

      this.close();
    },
    close() {
      this.$ionic.modalController.dismiss();
    },
    changePreview(event) {
      const input = event.target;
      if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => {
          this.recommendation.thumbnail = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
      }
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
      this.recommendation.category = categories[this.selectedCategory];
    }
  }
};
</script>

<style lang="scss" scoped>
#item-profile ion-img {
  max-width: 50%;
}
</style>
