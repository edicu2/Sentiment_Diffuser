<template>
<!-- 사용자가 모든 주문을 볼 수 있는 곳(주문내역) -->
<div>

<!-- 나중에 지우기 -->
<br><br><br><br>
<!-- 나중에 지우기 -->

 <div class="container">
    <h3>Mypage</h3>
    <hr>
    <h1 class="my-4">Order List</h1><br>

    <div class="row" style="margin:10px;background-color:#EEEFF3;height:350px">
        <div class="line">
          <div id="font2" class="text">GoldSangWon　様の決済ステータス</div>
        </div>

        <div class="col-lg-4 mb-4">
          <div class="sd">
            <h4 id="font2" class="center" style="margin-left:10px;">配送中</h4>
            <div>
               <img src="static/img/car3.png" alt="" weight="100px" height="100px">
               <div class="count">{{this.orders.length}}</div>
            </div>
          </div>
        </div>

        <div class="col-lg-4 mb-4">
          <div class="sd">
            <h4 id="font2" class="center" style="margin-left:10px;">配送完了</h4>
            <div>
              <img src="static/img/box2.png" alt="" weight="100px" height="100px">
              <div class="count">0</div>
            </div>
          </div>
        </div>

        <div class="col-lg-4 mb-4">
          <div class="sd">
            <h4 id="font2" class="center" style="margin-left:10px;">キャンセル/返品/交換</h4>
            <div>
              <img src="static/img/return2.png" alt="" weight="100px" height="100px">
              <div class="count">0</div>
            </div>
          </div>
        </div>
    </div>

    <div class="row position">
      <div>
        <div class="btn-group">
            <button id="font2" type="button" class="btn light">12月</button>
            <button id="font2" type="button" class="btn light">1月</button>
            <button id="font2" type="button" class="btn light">2月</button>
            <button id="font2" type="button" class="btn light">3月</button>
            <button id="font2" type="button" class="btn light">4月</button>
            <button id="font2" type="button" class="btn light">5月</button>
        </div>
      </div>

        <div class="col-2" style="margin-left:30px;">
          <input type="datetime-local" class="form-control" placeholder="datetime-local input">
        </div>
          <div style="font-size:20px">~</div>
        <div class="col-2">
          <input type="datetime-local" class="form-control" placeholder="datetime-local input">
        </div>
          <button id="font2" class="btn btn-secondary">照会</button>
    </div>

    <div class="row" style="margin:20px;">
        <div class="col-md-12">
        <br>

        <div class="row">
            <div class="tit_month">
                <img src="static/img/line.png" alt="" style="width:100%">
                <h4 style="position: relative;top:-45px">2019.06</h4>
            </div>
        </div>

        <table id="font2">
            <tr>
                <th>番号</th>
                <th>商品名</th>
                <th>数量</th>
                <th>価格</th>
                <th>配信ステータス</th>
            </tr>

            <tr v-for="(order,index) in orders" @key="index" :key="index">
                <th>{{index+1}}</th>
                <th style="text-align:left">
                    <img :src="order.product.product_img" alt="" width="100px" height="100px">
                    <span class="center">{{order.product.product_name}}</span>
                </th>
                <th style="width:100px">
                    <select class="form-control">
                     <option>1</option>
                    </select>
                </th>
                <th>{{order.product.product_price}}</th>
                <th style="color:blue">配送中</th>
            </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'

    export default {
        data(){
            return {
                user : this.$auth.getAuthenticatedUser(),
                orders : [],
                count: 0
            }
        },
        //마운트되기 전에 모든 사용자 주문을 가져오는 것을 반복하여 페이지 표시
        beforeMount(){
            axios.get('http://arominds.com:8000/api/users/GoldSangWon@gmail.com/orders')//this.user.id
            .then(response => {
                this.orders = response.data
                console.log(this.orders);
            })
            .catch(error => {
                console.error(error);
            })
        }
  }
</script>
<style scoped>
table {
    width: 100%;
    border-top: 1px solid #DEE0E2;
    border-collapse: collapse;
    font-size: 18px;
    text-align: center;
}

th, td {
    border-bottom: 1px solid #DEE0E2;
    padding: 10px;
}

a{
    color:red;
}

.sd{
    margin: 50px 0px 0px 60px;
}

.count{
    position: relative;
    left: 120px;
    top: -100px;
    font-size: 60px;
}

.line{
    background-color: #525F78;
    height: 60px;
    width: 100%;
    padding-left: 5px;
}

.text{
    color: white;
    float: left;
    margin: 15px;
    font-size: 20px;
    padding-right: 20px;
}

.tit_month{
    width: 100%;
    height: 39px;
    margin: -9px 0;
    text-align: center;
    margin-bottom: 35px;
}

.user{
  background-color: white;
  border-radius: 50%;
  width: 150px;
  height: 150px;
  padding: 10px;
}

.form-control{
    width: 100px;
}

.center{
    margin-left:10px;
}

.position{
    position: relative;
    left: 85px;
    top: -75px;
}

.light{
    background-color: white;
    border:solid 1px #D5D8DC;
    width: 80px;
}

.form-control{
    width: 160px;
}
</style>
