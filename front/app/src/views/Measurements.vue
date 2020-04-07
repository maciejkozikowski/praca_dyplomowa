<template>
  <div class="measurements">
    <v-container class="filter">
      <FilterComponent
        :parameter="parameter"
        :startDate="startDate"
        :endDate="endDate"
        @updateData="getMeasurements"
        @resetList="resetMeasurements"
      />
      <NewMeasurement @updateData="resetMeasurements" class="newMeasurement"/>
    </v-container>
    <v-layout xs10 sm10 md10 lg10>
      <MeasurementsTable :measurements="data" class="table" @updateData="resetMeasurements" />
      
    </v-layout>
  </div>
</template>
<script>
import axios from "axios";
import NewMeasurement from "../components/Measurement/NewMeasurement";
import MeasurementsTable from "../components/Measurement/MeasurementsTable";
import FilterComponent from "../components/Measurement/FilterComponent";

export default {
  name: "measurements",
  components: {
    MeasurementsTable,
    NewMeasurement,
    FilterComponent
  },
  data() {
    return {
      data: [],
      parameter: null,
      startDate: null,
      endDate: null
    };
  },
  methods: {
    getMeasurements(parameter, startDate, endDate) {
      axios
        .get(
          `http://localhost:8080/measurements/filter?parameter=${parameter}&startDate=${startDate}&endDate=${endDate}`
        )
        .then(response => {
          this.data = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    resetMeasurements() {
      axios
        .get(
          `http://localhost:8080/measurements/filter?parameter=null&startDate=null&endDate=null`
        )
        .then(response => {
          this.data = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  mounted() {
    this.getMeasurements(this.parameter, this.startDate, this.endDate);
  }
};
</script>
<style scoped>
.newMeasurement {
  padding: 20px
}
.table {
  margin-left: 2vw;
  margin-top: 2vw;
  margin-right: 8vw;
  width: 65vw;
  background-image: linear-gradient(
    to left,
    #d5d4d0 0%,
    #d5d4d0 1%,
    #eeeeec 31%,
    #efeeec 75%,
    #e9e9e7 100%
  );
}
.filter {
  width: 60vw;
}
</style>
