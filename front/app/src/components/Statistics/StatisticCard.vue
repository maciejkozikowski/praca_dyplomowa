<template>
  <v-container>
    <v-card class="card">
      <v-list-item>
        <v-list-item-content>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Average: {{ statistics.average }}
          </v-card-text>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Minimum: {{ statistics.minimum }}
          </v-card-text>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Maximum: {{ statistics.maximum }}
          </v-card-text>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Median: {{ statistics.median }}
          </v-card-text>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Sum: {{ statistics.sum }}
          </v-card-text>
          <v-card-text class="item">
            <v-icon class="blue--text">info</v-icon>
            Count: {{ statistics.count }}
          </v-card-text>
        </v-list-item-content>
      </v-list-item>
    </v-card>
  </v-container>
</template>
<script>
export default {
  name: "StatisticCard",
  props: {
    statistics: {
      type: Object,
      default: () => {}
    }
  }
};
</script>
<style scoped>
.card {
  background-color: #fee2f8;
  background-image: linear-gradient(315deg, #fee2f8 0%, #dcf8ef 74%);
  transition: all 0.3s ease-in-out;
}
.card:hover {
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.1);
}
.item {
  font-size: 22px;
  font-weight: 900;
}
</style>