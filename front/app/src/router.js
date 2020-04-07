import Vue from "vue";
import Router from "vue-router";
import Measurements from "./views/Measurements.vue";
import Parameters from "./views/Parameters.vue";
import Statistics from "./views/Statistics.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "measurements",
      component: Measurements
    },
    {
      path: "/parameters",
      name: "parameters",
      component: Parameters
    },
    {
      path: "/statistics",
      name: "statistics",
      component: Statistics
    }
  ]
});
