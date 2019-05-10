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
          <br>
          <div>
              <router-link class="view btn btn-outline-secondary1" id="list" :to="'/shop/'">List</router-link>
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
        axios.get('http://localhost:8000/api/products/' + this.$route.params.product)
        .then(response => {
          this.product = response.data
        })
      },

      imageChanged (e) {
        console.log(e.target.files[0])
        var fileReader = new FileReader()

        fileReader.readAsDataURL(e.target.files[0])

        fileReader.onload = (e) => {
          this.product.image = e.target.result
        }
      },

      update () {
        axios.put('http://localhost:8000/api/products/' + this.$route.params.product, this.product)
        .then(response => {
          swal("Updated!", "Your product has been updated!", "success")
          this.$router.push('/shop')
        })
      }
    }
  }
</script>

<style>
  #formEdit {
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
</style>
