import { createRouter, createWebHistory } from "vue-router";
import Index from "@/views/Index.vue";

const routes = [
  {
    path: "/",
    name: "Scenario Quality Checker",
    component: Index,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
