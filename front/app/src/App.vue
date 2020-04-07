<template>
  <v-app>
    <v-content class="main">
      <Navbar />
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import Navbar from "./components/Navbar.vue";

export default {
  components: { Navbar },
  name: "App"
};
</script>
<style scoped>
.main {
  background-image: linear-gradient(to top, #dfe9f3 0%, white 100%);
}
</style>