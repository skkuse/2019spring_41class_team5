<template>
  <ion-vue-modal modal-title="Edit Profile">
    <ion-list lines="full">
      <ion-item>
        <ion-label position="stacked">
          <span>ID</span>
          <ion-text color="danger">*id cannot be changed.</ion-text>
        </ion-label>
        <ion-input :value="user.id" readonly/>
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
        <ion-img :src="user.profileImage"/>
        <ion-button shape="clear" @click="handleProfileImageUploadButtonClick()">Change</ion-button>
        <input type="file" ref="inputProfileImage" accept="image/*" @change="changePreview" hidden>
      </ion-item>
    </ion-list>
  </ion-vue-modal>
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
      if (input.files && input.files[0]) {
        this.$base64.encode(input.files[0]).then(base64 => {
          this.user.profileImage = base64;
        });
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
