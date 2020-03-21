<template>
    <v-layout class="measurements">
        <h1>Measurements</h1>
        <v-flex v-if="error">
            {{errorInfo}}
        </v-flex>
            <v-card class="card" v-for="item in data" v-bind:key="item.id">
                <h1>value {{item.value.toFixed(2)}}</h1>
                <h1>parameterId {{item.parameter.id}}</h1>
                <h1>name {{item.parameter.name}}</h1>
                <h1>unit {{item.parameter.unit}}</h1>
                <h1>decimalPoints {{item.parameter.decimalPoints}}</h1>
                <h1>dateAdded {{item.parameter.dateAdded.substring(0,10)}}</h1>
                <h1>data {{item.date.substring(0,10)}}</h1>
                <h1>id {{item.value}}</h1>
            </v-card>
    </v-layout>
</template>

<script>
import axios from 'axios'

export default {
    name: 'measurements',
    data () {
        return {
            data: [],
            error: false,
            errorInfo: "Nie udało się pobrać danych, spróbuj ponownie później "
        }
    },
    mounted () {
    axios.get('http://localhost:8080/measurements/')
      .then(response => {
        this.data = response.data
        console.log(this.data)
      })
      .catch(error => {
        console.log(error)
        this.error = true
      })
  }
}
</script>
<style scoped>
.card {
    margin-top: 5%;
    padding: 40px;
}
</style>