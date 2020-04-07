<template>
  <v-simple-table fixed-header>
    <template v-slot:default>
      <thead>
        <tr>
          <th class="text-left">Name</th>
          <th class="text-left">Value</th>
          <th class="text-left">Time</th>
          <th class="text-left">Date</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in measurements" :key="item.name">
          <td class="tdName">{{ item.parameter.name }}</td>
          <td>{{ item.value.toFixed(item.parameter.decimalPoints) }} {{ item.parameter.unit }}</td>
          <td>{{ item.date.substring(11, 16) }}</td>
          <td>
            {{ item.date.substring(8, 10) }}-{{ item.date.substring(5, 8)
            }}{{ item.date.substring(0, 4) }}r.
          </td>
          <td>
            <v-btn class="error" @click="deleteMeasurement(item.id)">
              <v-icon>delete_forever</v-icon>Delete
            </v-btn>
          </td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>
<script>
import axios from "axios";
export default {
  name: "MeasurementsTable",
  props: {
    measurements: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    deleteMeasurement(id) {
      axios
        .delete(`http://localhost:8080/measurements?id=${id}`)
        .then(response => {
          console.log(response);
          this.$emit("updateData");
        })
        .catch(error => {
          console.log(error.response);
        });
    }
  }
};
</script>
<style scoped>
th {
  font-size: 18px;
}
.tdName {
  font-size: 16px;
  font-weight: bold;
}
</style>