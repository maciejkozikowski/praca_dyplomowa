<template>
  <v-container>
    <v-card class="card" v-for="parameter in allParameters" v-bind:key="parameter.id">
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="headline">
            {{
            parameter.name
            }}
          </v-list-item-title>
          <v-list-item-subtitle>Unit: [{{ parameter.unit }}]</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-card-text>Decimal precision points: {{ parameter.decimalPoints }}</v-card-text>
      <v-card-actions>
        <DeleteParameter
          class="delete"
          :singleParameter="parameter"
          @updateParameterList="$emit('updateParameterList')"
        />
      </v-card-actions>
    </v-card>
  </v-container>
</template>
<script>
import DeleteParameter from "../Parameters/DeleteParameter";
export default {
  name: "SingleParameterCard",
  components: {
    DeleteParameter
  },
  props: {
    allParameters: {
      type: Array,
      default: () => []
    },
    singleParameter: {
      type: Array,
      default: () => []
    }
  }
};
</script>
<style scoped>
.card {
  margin-left: 2%;
  margin-top: 4%;
  padding: 20px;
  background-image: linear-gradient(
    to top,
    #d5d4d0 0%,
    #d5d4d0 1%,
    #eeeeec 31%,
    #efeeec 75%,
    #e9e9e7 100%
  );
  transition: all 0.3s ease-in-out;
}
.card:hover {
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.1);
  transform: scale(1.1);
}
.delete {
  margin-left: 18%;
}
</style>
