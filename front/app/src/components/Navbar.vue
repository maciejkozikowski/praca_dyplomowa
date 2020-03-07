<template>
    <nav>
        <v-toolbar>
        <v-toolbar-title>
            <v-list>
        <v-list-tile v-for="link in links" :key="link.text" router :to="link.route">
          <v-list-tile-action>
            <v-btn>{{ link.text }}</v-btn>
          </v-list-tile-action>
          <v-list-tile-content>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
        </v-toolbar-title> 
        </v-toolbar>

    </nav>
</template>

<script>
export default {
    data() {
    return {
      links: [
        { text: 'Measurements', route: '/' },
        { text: 'Parameters', route: '/parameters' },
        { text: 'Statistics', route: '/statistics' },
      ]
    }
    }
}
</script>