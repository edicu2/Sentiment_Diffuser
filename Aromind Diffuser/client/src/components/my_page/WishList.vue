<template>
<div>
<!-- 나중에 지우기 -->
<br><br><br><br>
<!-- 나중에 지우기 -->

<div class="container">
    <h1 class="my-4">WishList</h1>
    <hr>

    <br><br>
    <div class="row margin">
      <div class="col">
        <ul class="progressbar">
          <li id="font2" class="active">カート</li>
          <li id="font2">注文/決済</li>
          <li id="font2">注文完了</li>
        </ul>
      </div>
    </div>

    <div class="row">
    <div class="col-sm-9">
        <div id="font2" class="product">商品情報</div>
        <table class="table shopping-cart-wrap">
        <thead class="text-muted" id="font2">
            <tr>
              <th scope="col" width="100">番号</th>
              <th scope="col">商品名</th>
              <th scope="col" width="120">数量</th>
              <th scope="col" width="120">価格</th>
              <th scope="col" class="text-right" width="200"></th>
            </tr>
        </thead>
        <tbody>
        <!-- <div style="display:none">{{this.getCarts()}}</div> -->
        <tr v-for="(cartproduct, index) in reversedMessage">
        <td style="padding-top: 40px;">{{index+1}}</td>
        <td>
            <div class="media">
                <div class="img-wrap"><img :src="cartproduct.product_img" alt="" width="100px" height="100px"></div>
                <div class="media-body">
                    <h6 id="font2" class="title text-truncate">{{cartproduct.product_name}}</h6>
                </div>
            </div>
        </td>
        <td>
            <select class="form-control" style="margin-top:25px;">
                <option>1</option>
            </select>
        </td>
        <td>
          <div class="price-wrap">
            <div class="price text-truncate">{{cartproduct.product_price}}</div>
          </div> <!-- price-wrap .// -->
        </td>
         <td class="text-right text-truncate">
          <a href="" class="btn btn-outline-danger btn-round" style="margin:30px;" @click="removeItem(index)"> × </a>
         </td>
        </tr>
        </tbody>
        </table>
        <br>

        <!-- 결제방법 -->
        <div id="font2" class="product">決済方法選択</div>
        <table class="table shopping-cart-wrap">
          <thead class="text-muted">
              <tr>
                <th id="font2" colspan="4" scope="col">他の決済を選択</th>
              </tr>
          </thead>
        <tbody>
          <tr>
            <td style="width:100px">
              <div :class="{'payment': this.checked == 0, 'payment_artive':this.checked == 1}">
                <img src="static/img/kakaopay.png" alt="" width="70px" height="70px" style="cursor:pointer" @click="check">
              </div>
            </td>
            <td style="width:100px">
              <div class="payment" style="border:solid 1px lightgray">
                <img src="static/img/NH.jpg" alt="" width="70px" height="70px" style="cursor:pointer">
              </div>
            </td>
            <td style="width:100px">
              <div class="payment" style="border:solid 1px lightgray">
                <img src="static/img/KB.jpg" alt="" width="70px" height="70px" style="cursor:pointer">
              </div>
            </td>
            <td></td>
          </tr>
        </tbody>
        </table>
    </div>

    <div class="col-sm-3">
      <!-- <table>
        <tr>
          <th> -->
              <div class="dlist-align h4">
                <div id="font2" class="total">総計</div>
              </div>
                <div id="font2" class="center"><strong>{{this.price + Math.floor(this.price*0.1)}}円</strong></div>

              <div class="dlist-align" style="margin-top: 15px;">
                  <div id="font2" class="total">合計: </div>
                  <div id="font2" class="text-right fontSize">{{this.price}}円</div>
              </div>

              <div class="dlist-align">
                  <div id="font2" class="total">税金:</div>
                  <div id="font2" class="text-right fontSize">{{Math.floor(this.price*0.1)}}円</div>
              </div>

              <hr>
              <div class="text-right">
                <button id="font2" type="button" class="btn btn-danger" style="width:250px;font-size:20px;" v-b-modal.modal-1>買う</button>
              </div>
          <!-- </th>
        </tr>
      </table> -->

    </div>
  </div>

  <!-- Modal Component-2 무드등 색 선택 -->
  <b-modal id="modal-1" @ok="handleOK" title="QR코드 결제" ok-variant="outline-danger" ok-title-html="확인" cancel-variant="outline-secondary" cancel-title-html="취소">
    <!-- color gradient -->
    <div class="area_view _qr_area" style="display: block;">
				<strong class="logo_pay">
          <img src="//t1.daumcdn.net/kakaopay/tesla/20181010/pg_web/images/logo_pay.png" class="img_g" alt="카카오페이">
        </strong>
			<p id="font2" class="desc_payask">QRコードをスキャンすると<br>注文確認ページに移動します。</p>
			<div class="area_qr">
				<div id="img_qr" style="text-align: center" class="img_g" alt="QR코드">
          <img style="border:solid 5px #FFF100;width:300px;height:300px;" src="static/img/qr.png"></div>
			</div>
		</div>
  </b-modal>
</div>
</div>
</template>

<script>
import axios from 'axios'

export default {
    data(){
        return{
          carts: {},
          //  checked:"1",
          products:[],
          cartproducts:[],
          Item:[],
          price:0,
          count:0,
          checked:0
        }
    },
    computed:{
      reversedMessage: function () {
        //this.getCarts();
        axios.get('http://arominds.com:8000/api/products')
          .then(response => {
            this.products = response.data
            this.getCarts();
        })
        return this.cartproducts;

    }
  },
  created(){
    this.getting();
  },
  methods: {
    removeItem:function(index){
      var cart = this.carts.items[rowId]//현재 로그인한 사용자
      if(confirm("삭제하겠습니까?")){
        localStorage.removeItem('cart'+index);
      }
    },
    getCarts(){
      for(var i = 0; i < 20; i++){
          this.Item[this.count] = localStorage.getItem('cart'+this.count);
          if(this.products[i].product_code == this.Item[this.count]){
              this.cartproducts[this.count] = this.products[i];
              this.price += this.products[i].product_price;
              this.count++;
              i = 0;
          }
      }
    },
    getting(){
      return this.products;
    },
    check: function(e) {
      this.checked = 1;
    },
    handleOK(){
      axios.post('http://arominds.com:8000/api/orders/', this.cartproducts)
        .then(response =>{
           console.log(response.data)
           swal("구매완료!", "구매해주셔서 고맙습니다", "success")
           this.$router.push('/order')//feed
        })
    }
  }
}
</script>

<style scoped>
.col-sm-3{
  margin-top: 55px;
}

.product{
  font-size: 25px;
  margin: 10px;
}

table {
  border-top: solid 2px black;
  width: 100%;
  border-collapse: collapse;
  font-size: 18px;
  text-align: center;
}

th, td {
  border-bottom: 1px solid #DEE0E2;
  padding: 10px;
}

.circle{
  float: right;
  width: 50px;
  height: 50px;
  background-color: #898989;
  border-radius: 50%;
}

/* progress bar */
  .progressbar {
      counter-reset: step;
  }
  .progressbar li {
      list-style-type: none;
      width: 30%;
      float: left;
      font-size: 25px;
      position: relative;
      text-align: center;
      text-transform: uppercase;
      color: #7d7d7d;
  }
  .progressbar li:before {
      width: 50px;
      height: 50px;
      content: counter(step);
      counter-increment: step;
      line-height: 44px;
      border: 2px solid #7d7d7d;
      display: block;
      text-align: center;
      margin: 0 auto 10px auto;
      border-radius: 50%;
      background-color: white;
  }
  .progressbar li:after {
      width: 100%;
      height: 2px;
      content: '';
      position: absolute;
      background-color: #7d7d7d;
      top: 26px;
      left: -50%;
      z-index: -1;
  }
  .progressbar li:first-child:after {
      content: none;
  }
  .progressbar li.active {
      color: green;
  }
  .progressbar li.active:before {
      border-color: #55b776;
  }
  .progressbar li.active + li:after {
      background-color: #55b776;
  }

  .margin{
    margin-bottom: 20px;
  }

.payment{
  border: solid 1px lightgray;
  width: 85px;
  height: 85px;
  padding: 5px;
}

.payment_artive{
  border: solid 1px indianred;
  width: 85px;
  height: 85px;
  padding: 5px;
}

.desc_payask{
  font-size: 20px;
  font-weight: bold;
}

.text-truncate{
  margin-top:30px;
}

.fontSize{
  font-size:20px;
}
.h4{
  margin: 20px;
  text-align: center;
}
.center{
  background-color: white;
  width: 283px;
  font-size: 35px;
  text-align: center;
  color: #EA0000;
  position: relative;
  left: -15px;
}

.col-sm-3{
  height:345px;
  background-color:#E9EDF6;
  border: solid 1px darkgray;
}
</style>
