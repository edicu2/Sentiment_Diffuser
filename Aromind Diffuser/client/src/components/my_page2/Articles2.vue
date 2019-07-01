<template>
    <div class="row" v-if="customcard_boards">
      <my-article id="cards" v-for="(customcard_board, index) in customcard_boards" v-bind:key="customcard_board.id" @delete-article="deleteArticle(customcard_board)" @view-article="hoverArticle(customcard_board)" @out-article="hoverOutArticle(customcard_board)" :authenticatedUser="authenticatedUser" :customcard_board="customcard_board" :rgb="rgb[index]" :rgbs="rgbs[index]"></my-article>
    </div>
</template>

<script type="text/javascript">
  import Article from '../article/Article.vue'
  // import swal from 'sweetalert'
  import EventBus from './EventBus'
  import axios from 'axios'

  export default {
    data () {
      return {
        customcard_boards: [],
        rgb: [],
        rgbs: [],
        active: false,
        cardUrl: '',
        test: 'aa'
      }
    },

//잠시 없앤거 -금상원
    computed: {
      authenticatedUser () {
        return this.$auth.getAuthenticatedUser()
      }
    },

    components: {
      'my-article': Article
    },

    created() {
      EventBus.$on('cardList', status => {
        this.cardUrl = status.substring(60)
        console.log(this.cardUrl)
        axios.get("http://arominds.com:8000/api/cardlist/" + this.cardUrl)
        /* axios.get('http://arominds.com:8000/api/articles') */
        .then(response=>{
          this.customcard_boards = response.data.customcard
          this.rgb = response.data.rgb
          this.rgbs = response.data.rgbs

          // console.log(response.data)
          // console.log(response.data)
          // console.log(this.rgb)
          // console.log(this.rgbs)

          var num = 0;
          this.customcard_boards.forEach((customcard_board) => {
            this.$set(customcard_board, 'hover', false)
          })//forEach
        })//then
      })//end EventBus

      // axios.get('http://arominds.com:8000/api/articles')
      // .then(response => {
      //   this.customcard_boards = response.data.customcard
      //   this.rgb = response.data.rgb
      //   this.rgbs = response.data.rgbs
      //
      //   /* console.log(response)
      //   console.log(response.data)
      //   console.log(response.data.rgb) */
      //   /* console.log(this.rgb)
      //   console.log(this.rgbs) */
      //
      //   var num = 0;
      //   this.customcard_boards.forEach((customcard_board) => {
      //     this.$set(customcard_board, 'hover', false)
      //   })//end forEach
      // })//end then
    },//end created()

    methods: {
      deleteArticle (customcard_board) {
        swal({
          title: "Are you sure?",
          text: "You will not be able to recover this article!",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "Yes, delete it!",
          closeOnConfirm: false
        },
        function() {
          axios.delete('http://arominds.com:8000/api/articles/' + custumcard_board.id)
          .then(response => {
            let index = this.customcard_boards.indexOf(customcard_board)

            this.customcard_boards.splice(index, 1)

            swal("Deleted!", "Your article has been deleted", "success");
          })
        }.bind(this)
        );
      },

      hoverArticle(customcard_board) {
        //console.log(customcard_board.id)
        var hoverr = document.getElementById(customcard_board.id)
        //hoverr.style.backgroundColor = 'black'
        hoverr.style.opacity = '0.7'
      },

      hoverOutArticle(customcard_board) {
        //console.log(customcard_board.id)
        var hoverr = document.getElementById(customcard_board.id)
        hoverr.style.opacity = '0'
      },

      selectCard(cardUrl){
        axios.get("http://arominds.com:8000/api/customcardboard/",  {
        params: {
          id: cardUrl
        }
      })
        .then(response=>{
          this.test = response.data
          console.log(this.test);
        })
      },//end selectCard

      /* hoverOil(customcard_board) {
        console.log(customcard_board.id)
        console.log('tt')
        var hoverr2 = document.getElementById(customcard_board.positive_perfume_name)
        //hoverr.style.backgroundColor = 'black'
        hoverr2.style.opacity = '0.7'
      },

      hoverOutOil(customcard_board) {
        console.log(customcard_board.id)
        var hoverr2 = document.getElementById(customcard_board.positive_perfume_name)
        hoverr2.style.opacity = '0'
      } */
    }
  }
</script>
