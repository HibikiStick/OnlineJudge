import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import { routes } from "./routers";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
