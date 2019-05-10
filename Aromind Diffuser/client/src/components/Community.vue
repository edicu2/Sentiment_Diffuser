<template>
  <div class="container" id="feed">
    <h2 id="font" style="font-weight: normal;">Community-Customcards</h2>
    <hr>
    <br>
    <div style="margin-bottom: 6%;">
      <router-link class="view btn btn-outline-secondary1" id="list" :to="'/articles/create/'">Create Article</router-link>
    </div>
    <my-articles></my-articles>
  </div>
</template>

<script>
  import Articles from './article/Articles.vue'

  export default {
    components: {
      'my-articles': Articles
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

  /* #font {
    font-family: 'Overpass:800', sans-serif;
  } */
</style>
