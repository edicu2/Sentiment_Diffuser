<template>
  <div>
    <div class="container" id="createArticle">
      <h2 style="font-weight: normal;">Community-Blending Aroma</h2>
      <hr>
      <br>
      <!-- <div class="row">
        <div class="col-md-12 col-md-offset-2">
          <div class="panel panel-default">
            <div class="panel-body">
              <form @submit.prevent="create">
                <div class="form-group">
                  <label for="">Upload Image</label>
                  <input type="file" class="form-control-file" @change="imageChanged">
                </div>
                <div class="form-group">
                  <label for="">Title</label>
                  <input name="title" type="text" class="form-control" v-validate="'required'" v-model="article.title">
                  <div class="help-block alert alert-danger" v-show="errors.has('title')">
                    {{ errors.first('title') }}
                  </div>
                </div>
                <div class="form-group">
                  <label for="">Description</label>
                  <textarea class="form-control" v-model="article.description"></textarea>
                </div>
                <input type="submit" class="btn btn-primary1 btn-block pull-right" value="Create">
              </form>
            </div>
            <br>
            <div>
              <router-link class="view btn btn-outline-secondary1" id="list" :to="'/community/'">List</router-link>
            </div>
          </div>
        </div>
      </div> -->

      <!-- Tab Menu -->
      <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item">
          <a class="nav-link active" id="custom-tab" data-toggle="tab" href="#custom" role="tab" aria-controls="custom" aria-selected="true">Custom Card</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" id="phone-tab" data-toggle="tab" href="#phone" role="tab" aria-controls="phone" aria-selected="false">Phone Custom Card</a>
        </li>
      </ul>

      <!-- Custom Card_Tab Content -->
      <div class="tab-content" id="myTabContent">
        <!-- Custom Card -->
        <div class="tab-pane fade show active" id="custom" role="tabpanel" aria-labelledby="custom-tab">
          <Customcard></Customcard>
        </div>

        <!-- Phone Card -->
        <div class="tab-pane fade" id="phone" role="tabpanel" aria-labelledby="phone-tab">
          <PhoneCustomcard></PhoneCustomcard>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import PhoneCustomcard from './PhoneCustomcard.vue'
  import Customcard from './Customcard.vue'

  export default {
    components: {
      'PhoneCustomcard': PhoneCustomcard,
      'Customcard':Customcard
    },

    data () {
      return {
        article: {
          title: '',
          description: ''
        }
      }
    },

    methods: {
      /* imageChanged (e) {
        console.log(e.target.files[0])
        var fileReader = new FileReader()

        fileReader.readAsDataURL(e.target.files[0])

        fileReader.onload = (e) => {
          this.article.image = e.target.result
        }
      }, */

      create () {
        /* this.$validator.updateDictionary({
          'al': {
              attributes: {
                  name: 'emri'
              }
          }
        })

        this.$validator.setLocale('al')

        this.$validator.validateAll().then(() => {
          this.$http.post('api/articles', this.article)
          .then(response => {
              this.$router.push('/community')
          })
        }) */

        axios.post('http://arominds.com:8000/api/articles', this.article)
        .then(response => {
          this.$router.push('/community')
        })
      }
    }
  }
</script>

<style>
  #createArticle {
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
