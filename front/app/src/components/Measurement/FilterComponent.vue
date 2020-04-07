<template>
  <v-expansion-panels>
    <v-expansion-panel>
      <v-expansion-panel-header>Filter</v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-row justify="space-around">
          <v-form ref="form">
            <v-select
              :items="parameterNames"
              label="Choose parameter"
              v-model="parameter"
              dense
              item-value="id"
              solo
              prepend-inner-icon="list"
              @change="updateData"
            ></v-select>
            <v-menu
              ref="menu1"
              v-model="menu1"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                  v-model="startDate"
                  label="Start date"
                  persistent-hint
                  prepend-icon="event"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="startDate"
                @change="updateData"
                no-title
                @input="menu1 = false"
              ></v-date-picker>
            </v-menu>
            <v-menu
              v-model="menu2"
              ref="menu2"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="290px"
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                  v-model="endDate"
                  label="End date"
                  persistent-hint
                  prepend-icon="event"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="endDate" @change="updateData" no-title @input="menu2 = false"></v-date-picker>
            </v-menu>
            <v-btn dark class="btnFilter" color="indigo" @click="resetList">Reset</v-btn>
          </v-form>
        </v-row>
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import axios from "axios";
export default {
  name: "FilterComponent",
  data() {
    return {
      parameters: [],
      parameterNames: [],
      date: new Date().toISOString().substr(0, 7),
      menu1: false,
      menu2: false,
      parameter: null,
      startDate: null,
      endDate: null,
      isData: false
    };
  },
  methods: {
    updateData() {
      var parameter = this.parameter;
      var startDate = this.startDate;
      var endDate = this.endDate;
      this.$emit("updateData", parameter, startDate, endDate);
    },
    resetList() {
      this.$refs.form.reset();
      this.parameter = null;
      this.startDate = null;
      this.endDate = null;
      this.isData = false;
      this.$emit("resetList");
    }
  },
  mounted() {
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
      });
  }
};
</script>
<style scoped>
.datePicker {
  background-color: white;
  margin-bottom: 5%;
  border-radius: 3px;
  font-size: 16px;
  padding-top: 4px;
  padding-bottom: 1px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  transition: 0.3s;
  height: 38px;
}
.btnFilter {
  margin-left: 35%;
}
</style>
