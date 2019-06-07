<!--
    Component: ItemDetailPage
    Objective: Show specific item detail
    Author   : Junhyun Kim <junbread@skku.edu>
    Functions: - [x] View item spec
               - [x] View item review summary
               - [ ] View item review details
               - [ ]Add/Edit/Delete user review
               - [ ] Dynamic loading for review lists
    Changelog: 
-->
<template>
  <ion-vue-page :page-title="`item Detail - ${item.name}`">
    <section id="item-detail" margin>
      <ion-grid>
        <ion-row>
          <ion-col size="6">
            <ion-img :src="item.image"/>
          </ion-col>
          <ion-col size="6">
            <div id="item-header">
              <h1>{{ item.name }}</h1>
              <span>{{ item.company }}</span>
            </div>
            <ul id="item-specs">
              <li v-for="(spec, index) in item.specs" :key="index">{{ spec }}</li>
            </ul>
          </ion-col>
        </ion-row>
        <ion-row align-items-center>
          <ion-col size="6">
            <ul id="item-vendors">
              <li v-for="(vendor, index) in item.vendors" :key="index">
                <a :href="vendor.link">{{ vendor.name }}</a>
              </li>
            </ul>
          </ion-col>
          <ion-col size="6" text-right>
            <ion-button size="small" fill="outline">
              <!-- TODO toggle bookmark -->
              <ion-icon name="add"/>
              <span>Add to Bookmark</span>
            </ion-button>
          </ion-col>
        </ion-row>
      </ion-grid>
    </section>
    <section id="review-analysis" margin>
      <h1>Review Analysis</h1>
      <ion-grid>
        <ion-row>
          <ion-col id="review-score" align-items-end>
            <h2>{{ item.score | percent(5) }}%</h2>
            <span>recommended</span>
          </ion-col>
          <ion-col id="review-score-chart"></ion-col>
        </ion-row>
        <ion-row id="review-keywords">
          <ion-col size="12">
            <h2>Most mentioned keywords</h2>
          </ion-col>
          <ion-col>
            <ul id="review-keywords-positive" class="review-keywords">
              <li
                v-for="keyword in item.keywords"
                :key
                v-if="keyword.type == 'positive'"
              >{{ keyword.name }}</li>
            </ul>
          </ion-col>
          <ion-col text-right>
            <ul id="review-keywords-negative" class="review-keywords">
              <li
                v-for="keyword in item.keywords"
                v-if="keyword.type == 'negative'"
              >{{ keyword.name }}</li>
            </ul>
          </ion-col>
        </ion-row>
      </ion-grid>
    </section>
    <section id="review-detail">
      <h1 margin-start>Item Reviews</h1>
      <ion-card v-for="review in reviews" :key="review.importance">
        <ion-card-header>
          <ion-card-subtitle>{{ review.author }}</ion-card-subtitle>
          <ion-card-title>{{ review.title }}</ion-card-title>
          <span v-if="review.score > 0">positive</span>
          <span v-else>negative</span>
        </ion-card-header>
        <ion-card-content>{{ review.content }}</ion-card-content>
      </ion-card>
    </section>
  </ion-vue-page>
</template>

<script>
export default {
  name: "item-detail-page",
  data() {
    return {
      prevRoute: null,
      item: {
        id: 1234,
        rank: 1,
        name: "Omen 16t",
        company: "Heulett-packard",
        keywords: [
          { name: "Performance", score: 15, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Extreme Gaming", score: 10, type: "positive" },
          { name: "Build Quality", score: 10, type: "negative" },
          { name: "Build Quality", score: 10, type: "negative" },
          { name: "Build Quality", score: 10, type: "negative" },
          { name: "Build Quality", score: 10, type: "negative" },
          { name: "Build Quality", score: 10, type: "negative" },
          { name: "Build Quality", score: 10, type: "negative" }
        ],
        image: "/img/item-sample.jpg",
        score: 4.7,
        specs: [
          "Intel® Core™ i7 processor",
          "NVIDIA® GeForce® GTX 1050",
          "8 GB memory; 1 TB HDD",
          '15.6" diagonal FHD display'
        ],
        vendors: [
          { name: "Amazon", link: "https://www.amazon.com" },
          { name: "BestBuy", link: "https://www.bestbuy.com" },
          { name: "Danawa", link: "https://danawa.com" }
        ]
      },
      reviews: [
        {
          id: 1,
          itemId: 1234,
          title: "리뷰 테스트",
          author: "김뫄뫄",
          content: "사장님이 맛있고 음식이 친절해요.",
          keywords: [
            { name: "맛있다", type: "positive", originalContext: "맛있고" },
            { name: "친절하다", type: "positive", originalContext: "친절해요" }
          ],
          reference: {
            name: "Gmarket",
            link: "http://gmarket.co.kr"
          },
          importance: 100,
          score: 95
        }
      ]
    };
  },
  methods: {
    getItem(itemId) {
      this.$http.get(`/items/${itemId}`).then(result => {
        this.item = result.data;
      });
    },
    getReviews(itemId) {
      const criteria = {
        itemId: itemId
      };

      this.$http.get(`/reviews`, { params: criteria }).then(result => {
        this.reviews = result.data;
      });
    },
    addItemToBookmark() {
      this.$http.post(`/users/bookmarks`);
    }
  },
  mounted() {
    // this method should be enabled after backend is configured
    //this.getItem(this.$route.params.id)
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.prevRoute = from;
    });
  },
  computed: {
    isPrevExist() {
      return this.prevRoute && this.prevRoute.matched.length !== 0;
    }
  }
};
</script>

<style lang="scss" scoped>
/* Page Global Sytles */
section h1 {
  font-size: 1.5em;
  font-weight: bold;
}

/* Item Details Section */

#item-header h1 {
  font-size: 1.5em;
  font-weight: bold;
  margin: 0;
}

#item-header span {
  font-size: 0.8em;
  color: var(--ion-color-primary);
}

#item-specs {
  margin-top: 0.5em;
  padding: 0;
}

#item-specs li {
  list-style: none;
  font-size: 0.7em;
  color: var(--ion-color-dark);
}

#item-specs li::before {
  content: "- ";
}

#item-vendors {
  margin: 0;
}

#item-vendors li {
  display: inline;
  list-style: none;
  font-size: 0.8em;
}

#item-vendors li:not(:first-of-type)::before {
  content: " | ";
  color: var(--ion-color-dark);
}

/* Review Analysis Section */

#review-score h2 {
  font-size: 4em;
  font-weight: bolder;
  margin-bottom: 0;
}

#review-keywords h2 {
  font-size: 1em;
  font-weight: bold;
}

.review-keywords li:nth-child(1) {
  font-weight: bold;
  font-size: 1.3em;
}

.review-keywords li:nth-child(2) {
  font-weight: bold;
  font-size: 1.1em;
}

.review-keywords li:nth-child(3) {
  font-size: 1em;
}

.review-keywords li {
  font-size: 0.8em;
}

#review-keywords-positive li {
  color: var(--ion-color-primary);
}

#review-keywords-negative li {
  color: var(--ion-color-dark-tint);
}
</style>
