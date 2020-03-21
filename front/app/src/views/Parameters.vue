<template>
    <v-layout>
        <h1>Parameters</h1>
        <v-card v-for="parameter in parameters" v-bind:key="parameter.name"> 
            {{parameter.name}}
        </v-card>
        <NewParameter/>
    </v-layout>
</template>

<script>
import axios from 'axios'
import NewParameter from './../components/Parameters/NewParameter'

export default {
    name: 'parameters',
    components: {
        NewParameter
    },
        data () {
        return {
            parameters: []
        }
    },
        mounted () {
    axios.get('http://localhost:8080/parameters')
      .then(response => {
        this.parameters = response.data
        console.log(this.data)
      })
      .catch(error => {
        console.log(error)
        this.error = true
      })
  }
}
</script>