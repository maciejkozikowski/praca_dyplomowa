<template>
  <div>
    <v-container>
    <NewParameter @updateParameterList="getParameters" />    
    </v-container>
    <v-layout xs10 sm10 md10 lg10>
      
      <SingleParameterCard
        :allParameters="parameters"
        class="singleCard"
        @updateParameterList="getParameters"
      />
      
    </v-layout>
  </div>
</template>
<script>
import axios from "axios";
import NewParameter from "../components/Parameters/NewParameter";
import SingleParameterCard from "../components/Parameters/SingleParameterCard";

export default {
  name: "parameters",
  components: {
    NewParameter,
    SingleParameterCard
  },
  props: {
    allParameters: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    getParameters() {
      axios
        .get("http://localhost:8080/parameters")
        .then(response => {
          this.parameters = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  data() {
    return {
      parameters: []
    };
  },
  mounted() {
    this.getParameters();
  }
};
</script>
<style scoped>
.singleCard {
  margin-top: 7vh;
  display: flex;
  flex-wrap: wrap;
}
</style>
