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
          path: "/ranking",
          components: {
            ranking: () => import("@/pages/RankPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          },
        },
        {
          path: "/mypage",
          components: {
            mypage: () => import("@/pages/MyPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "/recommendation",
          components: {
            recommendation: () => import("@/pages/RecommendationPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "/recommendation/:id",
          components: {
            recommendation: () => import("@/pages/RecommendationDetailPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "/search",
          components: {
            search: () => import("@/pages/SearchPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          }
        },
        {
          path: "/item-detail/:id",
          components: {
            ranking: () => import("@/pages/ItemDetailPage.vue")
          },
          meta: {
            permissions: ["user", "admin"]
          },
          alias: [
            "/ranking/item-detail/:id",
            "/recommendation/item-detail/:id"
          ]
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