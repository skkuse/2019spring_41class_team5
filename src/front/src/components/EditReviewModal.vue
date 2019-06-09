<template>
  <div>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="end">
          <ion-button @click="close()">
            Close
          </ion-button>
        </ion-buttons>
        <ion-title>Edit Profile</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content padding>
      <ion-list lines="full">
        <ion-item>
          <ion-label position="stacked">
            <span>ID</span>
            <ion-text color="danger"> *id cannot be changed.</ion-text>
          </ion-label>
          <ion-input :value="user.id" readonly />
        </ion-item>
        <ion-item>
          <ion-label position="stacked">Name</ion-label>
          <ion-input :value="user.name" @ionChange="user.name = $event.target.value"/>
        </ion-item>
        <ion-item>
          <ion-label position="stacked">Organization</ion-label>
          <ion-input :value="user.organization" @ionChange="user.organization = $event.target.value"/>
        </ion-item>
        <ion-item id="item-profile">
          <ion-label position="stacked">Profile Image</ion-label>
          <ion-img :src="user.image" />
          <ion-button
            shape="clear"
            @click="handleProfileImageUploadButtonClick()"
          >
            Change
          </ion-button>
          <input
            type="file"
            ref="inputProfileImage"
            accept="image/*"
            @change="changePreview"
            hidden
          />
        </ion-item>
      </ion-list>
    </ion-content>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "EditProfileModal",
  props: ["user"],
  methods: {
    handleProfileImageUploadButtonClick() {
      this.$refs.inputProfileImage.click();
    },
    changePreview(event) {
        const input = event.target;
        if(input.files && input.files[0]) {
            const reader = new FileReader();
            reader.readAsDataURL(input.files[0]);
            reader.onload = (e) => {
                this.user.image = e.target.result;
            }
        }
    },
    close() {
        this.$ionic.modalController.dismiss()
    }
  }
};
</script>

<style lang="scss" scoped>
#item-profile ion-img {
  max-width: 50%;
}
</style>
