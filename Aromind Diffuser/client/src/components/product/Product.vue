<template>
  <div class="col-md-4" style="margin-bottom: 1%;">
    <b-card no-body>
      <router-link class="view" :to="'/products/' + product.product_code">
        <img :src="product.product_img" alt="" style="width: 60%; padding-top: 5%;" class="card-img-top rounded mx-auto d-block">
      </router-link>

      <b-card-body>
        <b-card-title>{{ product.product_name }}</b-card-title>
        <b-card-sub-title class="mb-2">{{ product.product_price }} 원</b-card-sub-title>
        <b-card-text>
          Made by {{ product.business_number }}
        </b-card-text>
      </b-card-body>
      <b-list-group flush>
        <b-list-group-item>
          <a href="#" class="btn btn-outline-secondary1" style="margin-left: 5%; width: 45%;">
            Wish list
          </a>
          <a href="#" class="btn btn-primary1" style="width: 40%;">
            Buy
          </a>
        </b-list-group-item>
        <!-- <b-list-group-item>
          <a href="#" class="btn btn-danger" role="button" @click="$emit('delete-product')" style="margin-left: 8%;width: 40%;">
            Delete
          </a>
          <router-link class="btn btn-outline-info" :to="'/products/' + product.id + '/edit'" style="width: 40%;">
            Edit
          </router-link>
        </b-list-group-item> -->
      </b-list-group>
    </b-card>
  </div>
</template>

<script>
  export default {
    props: ['product', 'authenticatedUser'],
    //props: ['product'],
  }
</script>

<style>
  .view {
    color: black;
  }

  .view:visited {
    color: black;
    text-decoration: none;
  }

  .view:hover {
    color: rgb(234,119,142);
    text-decoration: none;
  }

  #font {
    /* font-family: 'Overpass', sans-serif; */
    font-family: 'Rubik', sans-serif;
  }

  #name {
    /* font-family: 'Overpass', sans-serif; */
    font-family: 'Rubik', sans-serif;
    font-weight: bold;
  }
</style>
