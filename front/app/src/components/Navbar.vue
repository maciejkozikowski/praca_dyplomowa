<template>
  <v-app-bar app dark src="../assets/navbar.jpg" class="nav">
    <v-toolbar-title>MyApp</v-toolbar-title>
    <v-menu transition="slide-x-transition" bottom right>
      <template v-slot:activator="{ on }">
        <v-btn class="hidden-sm-and-up white--text" text v-on="on">
          <v-icon>add</v-icon>Menu
        </v-btn>
      </template>
      <v-list>
        <v-list-item v-for="link in links" v-bind:key="link.text" :href="link.route">
          <v-list-item-title>{{ link.text }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-flex class="hidden-sm-and-down list">
      <v-btn class="buttons" text v-for="link in links" v-bind:key="link.text" :href="link.route">
        {{ link.text }}
        <v-icon>{{ link.icon }}</v-icon>
      </v-btn>
    </v-flex>
  </v-app-bar>
</template>
<script>
export default {
  data() {
    return {
      links: [
        {
          text: "Measurements",
          route: "/",
          icon: "star"
        },
        {
          text: "Parameters",
          route: "/parameters",
          icon: "priority_high"
        },
        {
          text: "Statistics",
          route: "/statistics",
          icon: "signal_cellular_alt"
        }
      ]
    };
  }
};
</script>
<style scoped>
.nav {
  align-items: center;
  justify-content: center;
  z-index: 20;
}
.buttons {
  display: flex;
  justify-content: center;
}
.list {
  justify-content: center;
  display: flex;
}
</style>
