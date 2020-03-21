<template>
  <v-footer
    dark
    padless
  >
    <v-card
      class="flex"
      flat
      tile
    >
      <v-card-title class="teal">
        <strong class="subheading"> Praca dyplomowa - Maciej Kozikowski</strong>
        <v-spacer></v-spacer>
      </v-card-title>

      <v-card-text class="py-2 white--text text-center">
        {{ new Date().getFullYear() }}r. — <strong>MyApp</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>