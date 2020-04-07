<template>
  <v-flex>
    <v-btn @click="deleteParameter(singleParameter.id)" class="error delete">
      <v-icon>delete_forever</v-icon>Delete
    </v-btn>
  </v-flex>
</template>
<script>
import axios from "axios";
export default {
  name: "DeleteParameter",
  props: {
    singleParameter: {
      type: Object,
      default: () => {}
    }
  },
  methods: {
    deleteParameter(id) {
      axios
        .delete(`http://localhost:8080/parameters?id=${id}`)
        .then(response => {
          console.log(response);
          this.$emit("updateParameterList");
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  }
};
</script>
