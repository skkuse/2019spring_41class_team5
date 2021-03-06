<!--
    Component: Mypage
    Objective: Manage user specific informations
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] View/Edit user profiles
               - [x] View/Delete bookmarks
               - [x] Logout
    Changelog: 
-->
<template>
  <ion-page class="ion-page" main>
    <ion-header>
      <ion-toolbar>
        <ion-buttons slot="start">
          <ion-back-button/>
        </ion-buttons>
        <ion-title>Mypage</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="handleLogoutButtonClick()">
            <ion-label>Logout</ion-label>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-content">
      <section id="profile" padding text-center>
        <ion-avatar inline>
          <ion-img :src="user.profileImage" v-if="user.profileImage != null && !user.profileImage.endsWith('undefined')"/>
          <ion-img src="/img/no-image.png" v-else />
        </ion-avatar>
        <h1>{{ user.name }}</h1>
        <h2>{{ user.organization }}</h2>
        <ion-button shape="round" size="small" @click="handleEditProfileButtonClick()">Edit</ion-button>
      </section>
      <section id="content" padding>
        <div id="list-bookmark">
          <h2 no-margin>Bookmarks</h2>
          <ion-card
            v-for="bookmark in bookmarks"
            :key="bookmark.id"
            no-margin
            margin-top
            @click="handleItemClick(bookmark)"
          >
            <ion-card-header>
              <ion-grid>
                <ion-row>
                  <ion-col size="8">
                    <ion-card-title>{{ bookmark.item.name }}</ion-card-title>
                    <ion-card-subtitle>{{ bookmark.item.company }}</ion-card-subtitle>
                  </ion-col>
                  <ion-col size="4">
                    <ion-img :src="bookmark.item.image | base64('jpg')"/>
                  </ion-col>
                </ion-row>
                <ion-row align-items-center>
                  <ion-col size="8">
                    <span>Added at {{ bookmark.addDate | moment("YYYY-MM-DD") }}</span>
                  </ion-col>
                  <ion-col size="4" text-right>
                    <ion-button
                      size="small"
                      fill="clear"
                      no-margin
                      no-padding
                      @click.stop="handleBookmarkDeleteButtonClick(bookmark)"
                    >
                      <ion-icon name="trash"/>
                      <span>Delete</span>
                    </ion-button>
                  </ion-col>
                </ion-row>
              </ion-grid>
            </ion-card-header>
          </ion-card>
        </div>
      </section>
    </ion-content>
  </ion-page>
</template>

<script>
import EditProfileModal from "../components/EditProfileModal";
export default {
  name: "my-page",
  data() {
    return {
      reviews: [],
      bookmarks: []
    };
  },
  methods: {
    /* Button event handlers */
    handleLogoutButtonClick() {
      this.$action
        .confirm("Do you really want to log out?")
        .then(() => this.logout());
    },
    handleEditProfileButtonClick() {
      this.$ionic.modalController
        .create({
          component: EditProfileModal,
          componentProps: {
            propsData: {
              user: this.$store.state.user
            }
          }
        })
        .then(a => {
          a.onDidDismiss().then(() => {
            this.$store.dispatch("updateUser", this.user);
          });
          a.present();
        });
    },
    handleBookmarkDeleteButtonClick(bookmark) {
      this.$action
        .confirm("Do you really want to delete this?")
        .then(() => this.deleteBookmark(bookmark.id));
    },
    handleItemClick(bookmark) {
      this.$router.push({
        path: `/mypage/item-detail/${bookmark.item.id}`
      });
    },

    /* Backend event handlers */
    getBookmarks() {
      this.$http
        .get(`/bookmarks?userId=${this.$store.state.user.id}`)
        .then(response => (this.bookmarks = response.data));
    },
    deleteBookmark(bookmarkId) {
      this.$http.delete(`/bookmarks/${bookmarkId}`).then(
        () => {
          // on success
          this.bookmarks = this.bookmarks.filter(
            bookmark => bookmark.id != bookmarkId
          );
          this.$action.toast("Deleted!");
        },
        () => {
          // on failed
          this.$action.toast("Failed to Remove Bookmark.");
        }
      );
    },
    getReviews() {
      this.$http
        .get(`/reviews?authorId=${this.$store.state.user.id}`)
        .then(data => (this.reviews = data));
    },
    updateReview(reviewId, review) {
      this.$http.patch(`/reviews/${reviewId}`, review).then(
        () => {
          // on success
          this.reviews.index = this.reviews.filter(
            review => review.id != reviewId
          );
        },
        () => {
          // on failed
          this.$action.toast("Failed to Remove Bookmark.");
        }
      );
    },
    deleteReview(reviewId) {
      this.$http.delete(`/reviews/${reviewId}`).then(
        () => {
          // on success
          this.bookmarks = this.bookmarks.filter(
            bookmark => bookmark.id != bookmarkId
          );
        },
        () => {
          // on failed
          this.$action.toast("Failed to Remove Bookmark.");
        }
      );
    },
    logout() {
      this.$store.dispatch("logout");
      this.$router.replace("/login");
      this.$action.toast("Successfully signed out");
    }
  },
  computed: {
    user() {
      return this.$store.state.user;
    }
  },
  created() {
    this.getBookmarks();
    //this.getReviews();
  }
};
</script>

<style lang="scss" scoped>
/* Profile Section */

#profile {
  background-color: var(--ion-color-light);
}

#profile ion-avatar {
  margin: 0 auto;
}

#profile h1 {
  font-weight: bold;
  font-size: large;
}

#profile h2 {
  margin-top: 0;
  color: var(--ion-color-medium);
  font-size: medium;
}
</style>
