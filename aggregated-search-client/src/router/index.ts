import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "../views/IndexPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "index",
    component: IndexPage,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
