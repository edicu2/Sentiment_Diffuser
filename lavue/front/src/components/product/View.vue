<template>
    <div>
        <div class="container" id="view">
            <h2>Shop-Aroma Oils</h2>
            <hr>
            <br>
            <div class="row">
                <div class="media align-items-center" style="margin-left: 17%;">
                    <img :src="'http://localhost:8000/' + product.image" alt="" style="width: 40%;" class="align-self-center mr-3">
                    <div class="media-body" style="margin-left: 13%;">
                        <h2 class="mt-0">{{ product.name }}</h2>
                        <h4>Price {{ product.price }}</h4>
                        <h5>Made by {{ product.user_id }}</h5>
                        <div class="input-group mb-3">
                            <h5>Quantity</h5>
                            <input type="number" class="form-control col-2" placeholder="1" aria-label="Recipient's username" aria-describedby="button-addon2" style="margin-left: 4%;">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary1" type="button" id="button-addon2">개</button>
                            </div>
                        </div>
                        <h5>Delivery 1~2day</h5>
                        <button class="btn btn-outline-secondary1 btn-lg" style="width: 30%;">Wish List</button>
                        <button class="btn btn-primary1 btn-lg" style="width: 30%;">To Order</button>
                    </div>
                </div>
            </div>
            <br>
            <div>
                <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
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
                <h2>{{ product.name }}</h2>
                <h5 style="margin-left: 3%;">{{ product.description }}</h5>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    created () {
        this.$http.get('api/products/' + this.$route.params.product)
        .then(response => {
            this.product = response.body
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
</style>