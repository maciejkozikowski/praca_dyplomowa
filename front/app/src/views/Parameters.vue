<template>
  <v-layout>
    <v-container xs11 sm11 md11 lg11 row wrap>
      <v-card v-for="parameter in parameters" v-bind:key="parameter.id" class="card">
        <v-flex>
          {{ parameter.name }}
          {{ parameter.unit }}
          {{ parameter.decimalPoints }}
        </v-flex>
        <v-flex>
    <v-btn @click="deleteParameter(parameter.id)"
      ><v-icon>delete_forever</v-icon>Delete</v-btn
    >
    </v-flex>
      </v-card>
    </v-container>
    <v-container xs1 sm1 md1 lg1>
      <NewParameter />
    </v-container>
  </v-layout>
</template>
<script>
import axios from "axios";
import NewParameter from "../components/Parameters/NewParameter";
export default {
  name: "parameters",
  components: {
    NewParameter
  },
  data() {
    return {
      parameters: []
    };
  },
  methods: {
    deleteParameter(id) {
      axios
        .delete(`http://localhost:8080/parameters?id=${id}`)
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
      .get("http://localhost:8080/parameters")
      .then(response => {
        this.parameters = response.data;
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
    width: 30vh;
    margin: 4%;
    padding: 30px;
}
</style>