<template>
  <div>
    <div class="container" id="view">
      <h2 id="font">Shop-Aroma Oils</h2>
      <hr>
      <br>

      <div class="row" style="margin-left: 20%;">
        <div>
          <b-media no-body class="align-items-center">
            <b-media-aside vertical-align="center">
              <b-img :src="product.product_img" width="382" height="460" />
            </b-media-aside>

            <b-media-body class="ml-3">
              <h2 class="mt-0">{{ product.product_name }}</h2>
              <h4>Price {{ product.product_price }}</h4>
              <h5 class="mb-0">Made by {{ product.business_number }}</h5>
              <br>
              <div>
                <b-input-group>
                  <h5 style="margin-right: 3%;">Quantity</h5>

                  <b-form-input type="number" min="1.00" placeholder="1" class="col-2"/>

                  <b-input-group-append>
                    <b-button variant="outline-secondary1">개</b-button>
                  </b-input-group-append>
                </b-input-group>
              </div>
              <br>
              <h5>Delivery 1~2day</h5>
              <br>
              <button class="btn btn-outline-secondary1 btn-lg">Wish List</button>
              <button class="btn btn-primary1 btn-lg">To Order</button>
            </b-media-body>
          </b-media>
        </div>
      </div>
      <br>
      <br>
      <div>
        <div id="menu" class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
          <div class="btn-group w-100" role="group" aria-label="First group">
            <button type="button" class="btn btn-primary1">상품 정보</button>
            <button type="button" class="btn btn-outline-secondary1">상품 리뷰</button>
            <button type="button" class="btn btn-outline-secondary1">Q&A</button>
            <button type="button" class="btn btn-outline-secondary1">판매자 정보</button>
          </div>
        </div>
      </div>
      <br>
      <div>
        <h2 style="float: left; color: rgb(234,119,142);">▶</h2>
        <h2>{{ product.product_name }}</h2>
        <h5 style="margin-left: 3%;">{{ product.product_content }}</h5>
      </div>
      <div>
        <router-link class="view btn btn-outline-secondary1" id="list" :to="'/shop/'">List</router-link>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    created () {
      axios.get('http://arominds.com:8000/api/products/' + this.$route.params.product)
      .then(response => {
        this.product = response.data
        console.log(response.data)
      })
    },

    data () {
      return {
        product: {}
      }
    },
  }
</script>

<style>
    #view {
        margin-top: 6%;
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
        font-family: 'Rubik', sans-serif;
    }

    #name {
        font-family: 'Rubik', sans-serif;
        font-weight: bold;
    }

    #menu {
        font-family: 'Noto Sans KR', sans-serif;
        font-weight: bold;
    }
</style>
