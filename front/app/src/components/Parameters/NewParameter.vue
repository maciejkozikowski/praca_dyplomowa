<template>
  <div>
    <v-dialog v-model="dialog" width="300px">
      <template v-slot:activator="{ on }">
        <v-btn class="red" dark v-on="on" rounded>Dalej</v-btn>
      </template>

      <v-card class="form">
        <v-app-bar dark color="#05518b">
          <v-toolbar-title>Wypełnij Formularz:</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-app-bar>
        <v-container class="card">
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="person.name"
              required
              :rules="nameRules"
              label="Imię"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <v-text-field
              v-model="person.surname"
              required
              :rules="surnameRules"
              label="Nazwisko"
              solo
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <v-text-field
              v-model="person.pesel"
              :rules="peselRules"
              required
              label="PESEL"
              solo
              @change="savePesel"
              dense
              prepend-inner-icon="account_circle"
            ></v-text-field>
            <v-text-field
              v-model="person.phoneNumber"
              :rules="phoneNumberRules"
              required
              label="Telefon komórkowy"
              solo
              dense
              prepend-inner-icon="stay_current_portrait"
            ></v-text-field>
            <v-text-field
              v-model="person.email"
              :rules="emailRules"
              required
              label="E-mail"
              solo
              dense
              prepend-inner-icon="email"
            ></v-text-field>
            <template>
              <v-menu
                ref="menu"
                v-model="menu"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                min-width="290px"
              >
                <template v-slot:activator="{ on }">
                  <v-text-field
                    solo
                    dense
                    v-model="person.date"
                    :rules="dateRules"
                    required
                    label="Data urodzenia"
                    prepend-inner-icon="event"
                    readonly
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker
                  ref="picker"
                  v-model="person.date"
                  locale="pl"
                  :max="new Date().toISOString().substr(0, 10)"
                  min="1950-01-01"
                  @change="savePesel"
                ></v-date-picker>
              </v-menu>
            </template>
          </v-form>
          <div class="errorMsg" v-if="error">PESEL nie zgadza się z datą urodzenia</div>
          <v-btn
            :disabled="!valid || error"
            @click="validate"
            class="btnCheck red white--text"
          >Sprawdź bezpłatnie</v-btn>
        </v-container>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar" :multi-line="multiLine">
      {{ text }}
      <v-btn color="white" text @click="snackbar = false">Zamknij</v-btn>
    </v-snackbar>
  </div>
</template>
<script>
export default {
  data: () => (	{
    dialog: false,
    menu: false,
    person: {
      name: null,
      surname: null,
      pesel: null,
      phoneNumber: null,
      email: null,
      date: null
    },
    multiLine: true,
    snackbar: false,
    text: "Dziękuję za uzupełnienie formularza",
    valid: true,
    error: false,
    nameRules: [
      v => !!v || "Imię jest wymagane",
      v => (v && v.length <= 20) || "Imię musi być krótsze niż 20 znaków",
      v => /^[a-zA-Z ]+$/.test(v) || "Imię jest niepoprawne"
    ],
    surnameRules: [
      v => !!v || "Nazwisko jest wymagane",
      v => (v && v.length <= 20) || "Nazwisko musi być krótsze niż 20 znaków",
      v => /^[a-zA-Z ]+$/.test(v) || "Nazwisko jest niepoprawne"
    ],
    peselRules: [
      v => !!v || "PESEL jest wymagany",
      v => /^[0-9]{11}$/.test(v) || "PESEL jest niepoprawny"
    ],
    phoneNumberRules: [
      v => !!v || "Telefon jest wymagany",
      v => /^[0-9]{9}$/.test(v) || "Telefon jest niepoprawny"
    ],
    emailRules: [
      v => !!v || "Email jest wymagany",
      v => /.+@.+\..+/.test(v) || "E-mail jest niepoprawny"
    ],
    dateRules: [v => !!v || "Data urodzenia jest wymagana"]
  }),
  watch: {
    menu(val) {
      val && setTimeout(() => (this.$refs.picker.activePicker = "YEAR"));
    }
  },
  methods: {
    savePesel(date) {
      if (this.person.pesel && this.person.date) {
        var peselPrepared = this.person.pesel.substring(0, 6);
        var datePrepared =
          this.person.date.substring(2, 4) +
          this.person.date.substring(5, 7) +
          this.person.date.substring(8, 10);
        if (peselPrepared == datePrepared) {
          this.error = false;
        } else {
          this.error = true;
        }
        this.$refs.menu.save(date);
      }
    },
    validate() {
      if (
        this.$refs.form.validate() &&
        this.peselPrepared == this.datePrepared
      ) {
        this.error = false;
        this.snackbar = true;
        this.dialog = false;
        var json = JSON.stringify(this.person);
        console.log(json);
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
  margin-left: 13%;
}
.errorMsg {
  margin-left: 3%;
  font-size: 14px;
  color: #ff5252;
}
</style>