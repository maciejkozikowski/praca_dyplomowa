<template>
  <v-bottom-navigation
    grow
    color="white"
    class="navbar"
    background-color="#4ea832"
  >
    <v-btn v-for="link in links" v-bind:key="link.text" :href="link.route">
      <span>{{link.text}}</span>
      <v-icon>{{link.icon}}</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
export default {
    data() {
    return {
      links: [
        { text: 'Measurements', route: '/', icon: "star", info: "Wypisuje bla bla bla"},
        { text: 'Parameters', route: '/parameters', icon: "priority_high", info: "Wypisuje bla bla bla"},
        { text: 'Statistics', route: '/statistics', icon:  "signal_cellular_alt", info: "Wypisuje bla bla bla"},
      ],
    }
    }
}
</script>
