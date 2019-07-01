<template>
    <div class="container"  id="formEdit">
        <h2>Shop-Edit Product</h2>
        <hr>
        <div class="row">
            <div class="col-md-12 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="form-group">
                            <label for="">Upload Image</label>
                            <input type="file" class="form-control-file" @change="imageChanged">
                        </div>
                        <div class="form-group">
                            <label for="">Name</label>
                            <input type="text" class="form-control" v-model="product.name">
                        </div>
                        <div class="form-group">
                            <label for="">Price</label>
                            <input type="number" class="form-control" v-model="product.price">
                        </div>
                        <div class="form-group">
                            <label for="">Description</label>
                            <textarea class="form-control" v-model="product.description"></textarea>
                        </div>

                        <button class="btn btn-primary1 btn-block pull-right" @click="update" v-show="product.name && product.price && product.description">
                            Update
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import swal from 'sweetalert'

export default {   
    created() {
        this.getProduct()
    },

    data () {
        return {
            product: {}
        }
    },

    methods: {
        getProduct () {
            this.$http.get('api/products/' + this.$route.params.product)
            .then(response => {
                this.product = response.body
            })
        },

        update () {
            this.$http.put('api/products/' + this.$route.params.product, this.product)
            .then(response => {
                swal("Updated!", "Your product has been updated!", "success")
                this.$router.push('/feed')
            })
        }
    }
}
</script>

<style>
    #formEdit {
        margin-top: 6%;
    }
</style>