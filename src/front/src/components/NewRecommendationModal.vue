<template>
  <div>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="end">
          <ion-button @click="close()">
            Close
          </ion-button>
        </ion-buttons>
        <ion-title>New Category</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content padding>
      <ion-list lines="full">
        <ion-item>
          <ion-label position="stacked">
            Category Name
          </ion-label>
          <ion-input @ionChange="recommendation.name = $event.target.value"/>
        </ion-item>
        <ion-item>
          <ion-label position="stacked">
            Description
          </ion-label>
          <ion-input @ionChange="recommendation.description = $event.target.value"/>
        </ion-item>
        <ion-item>
          <ion-label position="stacked">
            Category
          </ion-label>
          <ion-select>

          </ion-select>
        </ion-item>
        <ion-item>
          <ion-label position="stacked">
            Thumbnail Image
          </ion-label>
          <ion-img :src="recommendation.thumbnail" />
          <ion-button
            shape="clear"
            @click="handleThumbnailUploadButtonClick()"
          >
            Upload
          </ion-button>
          <input
            type="file"
            ref="inputThumbnailImage"
            accept="image/*"
            @change="changePreview"
            hidden
          />
        </ion-item>
        <ion-item>
          <ion-label position="stacked">
            Tags
          </ion-label>
        </ion-item>
      </ion-list>
      <ion-button expand="block" @click="close()">
        <ion-icon name="upload"></ion-icon>
        Upload
      </ion-button>
    </ion-content>
  </div>
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
      }
    }
  },
  methods: {
    handleThumbnailUploadButtonClick() {
      this.$refs.inputThumbnailImage.click();
    },
    handleUploadButtonClick() {
      this.$http.post()
      this.close();
    },
    close() {
        this.$ionic.modalController.dismiss()
    },
    changePreview(event) {
        const input = event.target;
        if(input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = (e) => {
                this.recommendation.thumbnail = e.target.result;
            }

            reader.readAsDataURL(input.files[0]);
        }
    },
  }
};
</script>

<style lang="scss" scoped>
#item-profile ion-img {
  max-width: 50%;
}
</style>
