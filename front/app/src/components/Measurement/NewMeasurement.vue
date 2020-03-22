<template>
  <div>
    <v-dialog v-model="dialog" width="300px">
      <template v-slot:activator="{ on }">
        <v-btn class="add" dark v-on="on" rounded>Add new measurement</v-btn>
      </template>

      <v-card class="form">
        <v-app-bar dark color="#05518b">
          <v-toolbar-title>Add measurement:</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-app-bar>
        <v-container class="card">
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-select
              :items="parameterNames"
              label="Choose parameter"
              v-model="measurement.parameter"
              dense
              item-value="id"
              solo
              :rules="parameterRules"
              prepend-inner-icon="account_circle"
            ></v-select>
            <v-text-field
              v-model="measurement.value"
              required
              :rules="valueRules"
              label="Value"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <div class="datePicker">
              <datetime
                class="theme-green datePicker"
                :minute-step="5"
                placeholder="Choose date/time"
                type="datetime"
                :max-datetime="dateNow.toISOString()"
                v-model="measurement.date"
                value-zone="local"
                zone="local"
              />
            </div>
          </v-form>
          <v-btn
            :disabled="!valid || error"
            @click="validate"
            class="btnCheck green white--text"
            ><v-icon>add</v-icon>Add</v-btn
          >
        </v-container>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      dateNow: new Date(),
      dialog: false,
      menu: false,
      parameters: [],
      parameterNames: [],
      measurement: {
        parameter: null,
        value: null,
        date: null
      },
      multiLine: true,
      valid: true,
      error: false,
      parameterRules: [v => !!v || "Parameter is required"],
      valueRules: [
        v => !!v || "Value is required",
        v => (v && v.length <= 20) || "Value has to be shorter than 20 chars",
        v => /^((-?\d+)|(-?\d*.\d+))?$/.test(v) || "Wartość musi być liczbą"
      ]
    };
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
        this.error = true;
      });
  },
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = "YEAR"));
    }
  },
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        this.error = false;
        this.dialog = false;
        this.measurement.date = this.measurement.date.substring(0, 19);
        axios
          .post("http://localhost:8080/measurements/", this.measurement)
          .then(resp => {
            console.log(resp.data);
            window.location.reload();
          })
          .catch(error => {
            console.log(error.response);
          });
      } else {
        this.error = true;
      }
    }
  }
};
</script>
<style scoped>
.dialog {
  color: white;
  padding: 10%;
  background-color: #05518b;
}
.card {
  background-color: #05518b;
}
.textField {
  background-color: white;
}
.btnCheck {
  color: red;
  margin-left: 35%;
}
.add {
  position: fixed;
  margin-left: 20%;
  justify-content: center;
}
.datePicker {
  background-color: white;
  margin-bottom: 5%;
  border-radius: 3px;
  font-size: 16px;
  padding-top: 4px;
  padding-bottom: 1px;
}
</style>
