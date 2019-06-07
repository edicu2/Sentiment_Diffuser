<template>
  <div>
    <div class="container" id="view">
      <h2 id="font">Shop-Aroma Oils</h2>
      <hr>
      <br>

      <div class="row" style="margin-left: 10%;">
        <div>
          <b-media no-body class="align-items-center">
            <b-media-aside vertical-align="center">
              <b-img :src="product.product_img" width="382" height="460" />
            </b-media-aside>

            <b-media-body class="ml-3">
              <hr style="height: 2px; background: black;">
              <h2 class="mt-0" id="font2">{{ product.product_name }}</h2>
              <hr>
              <!-- <h4>Price <span style="margin-left: 20%;">{{ product.product_price }}</span> <span id="font2">円</span></h4>
              <h5 class="mb-0">Made by <span style="margin-left: 14%;">{{ product.business_number }}</span></h5>
              <br>
              <div>
                <b-input-group>
                  <h5 style="margin-right: 3%;">Quantity</h5>

                  <b-form-input type="number" min="1.00" placeholder="1" class="col-2" style="margin-left: 12%;"/>

                  <b-input-group-append>
                    <b-button variant="outline-secondary1">개</b-button>
                  </b-input-group-append>
                </b-input-group>
              </div>
              <br>
              <h5>Delivery <span style="margin-left: 15%;">1~2day</span></h5> -->
              <table class="table table-borderless" style="margin-left: 6%;">
                <thead>
                  <tr>
                    <th scope="col"><h4>Price</h4></th>
                    <th scope="col"><h4>{{ product.product_price }} <span id="font2">円</span></h4></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row"><h5>Made by</h5></th>
                    <td colspan="2"><h5>{{ product.business_number }}</h5></td>
                  </tr>
                  <tr>
                    <td colspan="2">
                      <b-input-group>
                        <h5 style="margin-right: 3%;">Quantity</h5>

                        <select class="form-control" id="exampleFormControlSelect1" style="margin-left: 31%; margin-right: 25%;">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>

                        <!-- <b-form-input type="number" min="1.00" placeholder="1" class="col-2" style="margin-left: 31%;"/> -->

                        <!-- <b-input-group-append>
                          <b-button variant="outline-secondary1">개</b-button>
                        </b-input-group-append> -->
                      </b-input-group>
                    </td>
                  </tr>
                  <tr>
                    <th scope="row"><h5>Delivery</h5></th>
                    <td><h5>1~2day</h5></td>
                  </tr>
                </tbody>
              </table>
              <hr>
              <button class="btn btn-outline-secondary1 btn-lg" id="font2" style="width: 44%; margin-left: 3%;">カートに入れる</button>
              <button class="btn btn-primary1 btn-lg" id="font2" style="width: 44%; margin-left: 3%;">今すぐ買う</button>
            </b-media-body>
          </b-media>
        </div>
      </div>
      <br>
      <br>
      <div>
        <div id="menu" class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
          <div class="btn-group w-100" role="group" aria-label="First group">
            <button type="button" class="btn btn-primary1" id="font2">商品情報</button>
            <button type="button" class="btn btn-outline-secondary1" id="font2">商品レビュー</button>
            <button type="button" class="btn btn-outline-secondary1" id="font2">Q&A</button>
            <button type="button" class="btn btn-outline-secondary1" id="font2">販売者情報</button>
          </div>
        </div>
        <div>
          <table class="table table-bordered">
            <thead>
              <tr>
                <th id="font2" scope="col" style="width: 24.8%; background-color: #FFD9D8; border-color:#ea778e; border-top: 0px;">商品状態</th>
                <td id="font2" scope="col" style="width: 27.8%; border-color:#ea778e; border-top: 0px;">新商品</td>
                <th id="font2" scope="col" style="width: 21.3%; background-color: #FFD9D8; border-color:#ea778e; border-top: 0px;">商品番号</th>
                <td id="font2" scope="col" style="border-color:#ea778e; border-top: 0px;">{{ product.product_code }}</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th id="font2" scope="row" style="background-color: #FFD9D8; border-color:#ea778e;">製造社</th>
                <td id="font2" style="border-color:#ea778e;">{{ product.product_name }}</td>
                <th id="font2" style="background-color: #FFD9D8; border-color:#ea778e;">ブランド</th>
                <td id="font2" style="border-color:#ea778e;">{{ product.product_name }}</td>
              </tr>
              <tr>
                <th id="font2" scope="row" style="background-color: #FFD9D8; border-color:#ea778e;">モデル名</th>
                <td id="font2" style="border-color:#ea778e;">アロマオイル</td>
                <th id="font2" style="background-color: #FFD9D8; border-color:#ea778e;">原産地</th>
                <td id="font2" style="border-color:#ea778e;">イギリス</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <br>
      <!-- <div>
        <h2 style="float: left; color: rgb(234,119,142);">▶</h2>
        <h2 id="font2">{{ product.product_name }}</h2>
        <h5 style="margin-left: 3%;" id="font2">{{ product.product_content }}</h5>
      </div> -->
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

    #font3 {
        font-family: 'Kosugi Maru', sans-serif;
        font-weight: lighter;
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
