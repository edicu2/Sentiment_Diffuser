<template>
  <div>
    <div class="container" id="feed">
      <h2 id="font" style="font-weight: normal;">Shop-Aroma Oils</h2>
      <hr>
      <br>
      <div style="margin-bottom: 6%;">
        <router-link style="width: 12%;" class="view btn btn-outline-secondary1" id="list" :to="'/products/create/'">Create Product</router-link>
      </div>
      <my-products></my-products>
    </div>
  </div>
</template>

<script>
  import Products from './product/Products.vue'

  export default {
    components: {
      'my-products': Products
    }
  }
</script>

<style>
  #feed {
    padding-top: 6%;
  }

  #list {
    color: rgb(234,119,142);
    width: 11%;
    float: right;
  }

  #list:hover {
    color: white;
  }

  #font {
    /* font-family: 'Overpass:800', sans-serif; */
    font-family: 'Rubik', sans-serif;
  }
</style>
