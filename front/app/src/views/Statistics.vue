<template>
  <div>
    <v-container class="filter">
      <FilterComponent
        :parameter="parameter"
        :startDate="startDate"
        :endDate="endDate"
        :isData="isData"
        @updateData="getStatistics"
        @resetList="getStatistics"
      />
      <StatisticCard :statistics="data" v-if="isData" />
      <v-flex class="noData" v-if="!isData">No data</v-flex>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import FilterComponent from "../components/Measurement/FilterComponent";
import StatisticCard from "../components/Statistics/StatisticCard";
export default {
  name: "statistics",
  components: {
    FilterComponent,
    StatisticCard
  },
  data() {
    return {
      data: {},
      parameter: null,
      startDate: null,
      endDate: null,
      isData: false
    };
  },
  methods: {
    getStatistics(parameter, startDate, endDate) {
      if (parameter) {
        axios
          .get(
            `http://localhost:8080/measurements/statistics?parameter=${parameter}&startDate=${startDate}&endDate=${endDate}`
          )
          .then(response => {
            this.data = response.data;
            this.isData = true;
          })
          .catch(error => {
            console.log(error);
            this.isData = false;
          });
      } else {
        this.isData = false;
      }
    }
  }
};
</script>
<style scoped>
.filter {
  width: 60vw;
}
.noData {
  margin-top: 2vh;
}
</style>