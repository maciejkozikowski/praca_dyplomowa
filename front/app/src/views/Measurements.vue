<template>
  <v-layout class="measurements">
    <v-select
      :items="parameterNames"
      label="Choose parameter"
      v-model="parameters"
      dense
      item-value="id"
      solo
      prepend-inner-icon="account_circle"
    ></v-select>
    <v-container xs10 sm10 md10 lg10>
      <v-card class="card" v-for="item in data" v-bind:key="item.id">
        <h1>{{ item.parameter.name }}</h1>
        <h1>{{ item.value.toFixed(item.parameter.decimalPoints) }} {{ item.parameter.unit }}</h1>
        <h1>{{ item.date.substring(11, 16) }} {{ item.date.substring(8, 10) }}-{{ item.date.substring(5, 8) }}{{ item.date.substring(0, 4) }}</h1>
        <v-btn @click="deleteMeasurement(item.parameter.id)"
          ><v-icon>delete_forever</v-icon>Delete</v-btn
        >
      </v-card>
    </v-container>
    <v-container xs2 sm2 md2 lg2>
      <NewMeasurement />
    </v-container>
  </v-layout>
</template>
<script>
import axios from "axios";
import NewMeasurement from "../components/Measurement/NewMeasurement";

export default {
  name: "measurements",
  components: {
    NewMeasurement
  },
  data() {
    return {
      data: [],
      error: false,
      parameters: [],
      parameterNames: []
    };
  },
  methods: {
    deleteMeasurement(id) {
      axios
        .delete(`http://localhost:8080/measurements?id=${id}`)
        .then(response => {
          console.log(response);
          window.location.reload();
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  },
  mounted() {
    axios
      .get("http://localhost:8080/measurements/")
      .then(response => {
        this.data = response.data;
      })
      .catch(error => {
        console.log(error);
        this.error = true;
      });
    axios
      .get("http://localhost:8080/parameters")
      .then(response => {
        this.parameters = response.data;
        this.parameters.map(parameter => {
          this.parameterNames.push(parameter.name);
        });
      })
      .catch(error => {
        console.log(error);
        this.error = true;
      });
  }
};
</script>
<style scoped>
.card {
  margin-top: 5%;
  padding: 40px;
}
</style>
