<template>
  <div>
    <v-dialog v-model="dialog" width="300px">
      <template v-slot:activator="{ on }">
        <v-btn class="add" dark v-on="on" rounded>Add new parameter</v-btn>
      </template>

      <v-card class="form">
        <v-app-bar dark color="#05518b">
          <v-toolbar-title>Fill the form:</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-app-bar>
        <v-container class="card">
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="parameter.name"
              required
              :rules="nameRules"
              label="Parameter name"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <v-text-field
              v-model="parameter.unit"
              required
              :rules="parameterRules"
              label="Unit"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <v-text-field
              v-model="parameter.decimalPoints"
              :rules="decimalPointsRules"
              required
              label="Decimal place"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <div class="message" v-if="error">
              This parameter already exists
            </div>
            <template>
              <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
              </v-menu>
            </template>
          </v-form>
          <v-btn
            :disabled="!valid || error"
            @click="validate"
            class="btnCheck red white--text"
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
  data: () => ({
    dialog: false,
    menu: false,
    parameter: {
      name: null,
      unit: null,
      decimalPoints: null
    },
    multiLine: true,
    valid: true,
    error: false,
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 20) || "Name has to be shorter than 20 chars",
      v => /^[a-zA-Z]+$/.test(v) || "Name is invalid"
    ],
    parameterRules: [
      v => !!v || "Unit is required",
      v => (v && v.length <= 20) || "Unit has to be shorther than 20 chars"
    ],
    decimalPointsRules: [
      v => !!v || "Decimal place is required",
      v => /^[0-9]$/.test(v) || "Decimal place is invalid"
    ]
  }),
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
        axios
          .post("http://localhost:8080/parameters/", this.parameter)
          .then(resp => {
            console.log(resp.data);
            window.location.reload();
          })
          .catch(error => {
            console.log(error.response);
            this.error = true;
            this.dialog = true;
          });
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
.add {
  position: fixed;
  margin-left: 20%;
  justify-content: center;
}
.textField {
  background-color: white;
}
.btnCheck {
  margin-left: 35%;
}
.message{
  color: red;
  font-weight: 900;
}
</style>
