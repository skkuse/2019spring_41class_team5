import Vue from "vue";
import { IonicVueRouter } from "@ionic/vue";
import store from "./store.js";

Vue.use(IonicVueRouter);

const router = new IonicVueRouter({
  base: process.env.BASE_URL,
  mode: "history",
  routes: [
    {
      path: "/",
      component: () => import("@/components/TabView.vue"),
      meta: { permissions: ["user", "admin"] },
      children: [
        {
          path: "/ranking",
          components: { ranking: () => import("@/pages/RankPage.vue") },
          meta: { permissions: ["user", "admin"] }
        },
        {
          path: "/mypage",
          components: { mypage: () => import("@/pages/MyPage.vue") },
          meta: { permissions: ["user", "admin"] }
        },
        {
          path: "/recommendation",
          components: {
            recommendation: () => import("@/pages/RecommendationPage.vue")
          },
          meta: { permissions: ["user", "admin"] }
        },
        {
          path: "/recommendation/:id",
          components: {
            recommendation: () => import("@/pages/RecommendationDetailPage.vue")
          },
          meta: { permissions: ["user", "admin"] }
        },
        {
          path: "/search",
          components: { search: () => import("@/pages/SearchPage.vue") },
          meta: { permissions: ["user", "admin"] }
        },
        {
          path: "/item-detail/:id",
          components: { ranking: () => import("@/pages/ItemDetailPage.vue") },
          meta: { permissions: ["user", "admin"] },
          alias: [
            "/ranking/item-detail/:id",
            "/recommendation/item-detail/:id"
          ],
          props: true
        }
      ]
    },
    {
      path: "/login",
      component: () => import("@/pages/Login.vue"),
      meta: { permissions: ["all"] }
    },
    {
      path: "/signup",
      component: () => import("@/pages/Signup.vue"),
      meta: { permissions: ["all"] }
    },
    {
      path: "*",
      redirect: "/"
    }
  ]
});

// Authorization
router.beforeEach((to, from, next) => {
  to.matched.forEach(entry => {
    const permissions = entry.meta.permissions;
    if (permissions.includes("user")) {
      if (store.state.token) next();
      else {
        next("/login");
      }
    } else if (permissions.includes("admin")) {
      if (store.state.token && store.state.user.authority === "admin") next();
      else {
        next(false);
      }
    } else next();
  });
});

export default router;
