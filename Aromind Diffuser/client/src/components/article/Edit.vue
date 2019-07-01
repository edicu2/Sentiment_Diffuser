<template>
  <div class="container"  id="formEdit">
    <h2>Edit Article</h2>
    <hr>
    <div class="row">
      <div class="col-md-12 col-md-offset-2">
        <div class="panel panel-default">
          <div class="panel-body">
            <div class="form-group">
              <label for="">Title</label>
              <input type="text" class="form-control" v-model="article.title">
            </div>
            <div class="form-group">
              <label for="">Writer</label>
              <input type="text" class="form-control" v-model="article.user_id">
            </div>
            <div class="form-group">
              <label for="">Description</label>
              <textarea class="form-control" v-model="article.description"></textarea>
            </div>

            <button class="btn btn-primary1 btn-block pull-right" @click="update" v-show="article.title && article.user_id && article.description">
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
  import axios from 'axios'

  export default {
    created() {
      this.getArticle()
    },

    data () {
      return {
        article: {}
      }
    },

    methods: {
      getArticle () {
        this.$http.get('api/articles/' + this.$route.params.article)
        .then(response => {
          this.article = response.body
        })
      },

      /* imageChanged (e) {
        console.log(e.target.files[0])
        var fileReader = new FileReader()

        fileReader.readAsDataURL(e.target.files[0])

        fileReader.onload = (e) => {
            this.product.image = e.target.result
        }
      }, */

      update () {
        axios.put('http://localhost:8000/api/articles/' + this.$route.params.article, this.article)
        .then(response => {
          swal("Updated!", "Your article has been updated!", "success")
          this.$router.push('/community')
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
