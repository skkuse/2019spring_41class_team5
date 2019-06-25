import Vue from "vue";
import {
  IonicVueRouter
} from "@ionic/vue";

Vue.use(IonicVueRouter);

const router = new IonicVueRouter({
  base: process.env.BASE_URL,
  mode: "history",
  routes: [{
      path: "/",
      component: () => import("@/components/TabView.vue"),
      meta: {
        permissions: ["user", "admin"]
      },
      redirect: '/ranking',
      children: [{
          path: "ranking",
          components: {
            ranking: () => import("@/pages/RankPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          },
        },
        {
          path: "ranking/item-detail/:id",
          components: {
            ranking: () => import("@/pages/ItemDetailPage.vue"),
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "mypage",
          components: {
            mypage: () => import("@/pages/MyPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "mypage/item-detail/:id",
          components: {
            mypage: () => import("@/pages/ItemDetailPage.vue"),
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "recommendation",
          components: {
            recommendation: () => import("@/pages/RecommendationPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "recommendation/detail/:recommendationId/item-detail/:id",
          components: {
            recommendation: () => import("@/pages/ItemDetailPage.vue"),
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "recommendation/detail/:id",
          components: {
            recommendation: () => import("@/pages/RecommendationDetailPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "search",
          components: {
            search: () => import("@/pages/SearchPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "search/item-detail/:id",
          components: {
            search: () => import("@/pages/ItemDetailPage.vue"),
          },
          meta: {
            permissions: ["user", "admin"]
          }
        }
      ]
    },
    {
      path: "/login",
      component: () => import("@/pages/Login.vue"),
      meta: {
        permissions: ["all"]
      }
    },
    {
      path: "/signup",
      component: () => import("@/pages/Signup.vue"),
      meta: {
        permissions: ["all"]
      }
    },
    {
      path: "*",
      redirect: "/"
    }
  ]
});

export default router;