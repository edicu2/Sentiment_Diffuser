<template>
  <div id="app">
    <navbar></navbar>
    <router-view></router-view>
    <bottom></bottom>
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
import Bottom from './components/Footer.vue'

export default {
  components: {
    'navbar' : Navbar,
    'bottom' : Bottom
  }
}
</script>

<style>
  .btn-primary1 {
    background: rgb(234,119,142);
    /* background: linear-gradient(10deg, rgba(237,153,117,1) 0%, rgba(234,119,142,1) 33%, rgba(234,119,142,1) 83%, rgba(213,119,147,1) 100%); */
    color: white;
  }

  .btn-outline-secondary1 {
    border-color: rgb(234,119,142);
    color: rgb(234,119,142);
  }

  .btn-outline-secondary1:hover {
    background-color: rgb(234,119,142);
    color: white;
  }
</style>
