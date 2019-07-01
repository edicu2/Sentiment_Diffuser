<template>
    <div>
        <div class="container" id="createProduct">
            <h2>Shop-Create Product</h2>
            <hr>
            <br>
            <div class="row">
                <div class="col-md-12 col-md-offset-2">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form @submit.prevent="create">
                                <div class="form-group">
                                    <label for="">Upload Image</label>
                                    <input type="file" class="form-control-file" @change="imageChanged">
                                </div>
                                <div class="form-group">
                                    <label for="">Name</label>
                                    <input name="name" type="text" class="form-control" v-validate="'required'" v-model="product.name">
                                    <div class="help-block alert alert-danger" v-show="errors.has('name')">
                                        {{ errors.first('name') }}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="">Price</label>
                                    <input name="price" type="number" class="form-control" v-validate="'max_value:50|min_value:1'" v-model="product.price">
                                    <div class="help-block alert alert-danger" v-show="errors.has('price')">
                                        {{ errors.first('price') }}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="">Description</label>
                                    <textarea class="form-control" v-model="product.description"></textarea>
                                </div>

                                <input type="submit" class="btn btn-primary1 btn-block pull-right" value="Create">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            product: {
                name: '',
                price: 0,
                description: '',
                image: ''
            }
        }
    },

    methods: {
        imageChanged (e) {
            console.log(e.target.files[0])
            var fileReader = new FileReader()

            fileReader.readAsDataURL(e.target.files[0])

            fileReader.onload = (e) => {
                this.product.image = e.target.result
            }
        },

        create () {
            this.$validator.updateDictionary({
                'al': {
                    attributes: {
                        name: 'emri'
                    }
                }
            })

            this.$validator.setLocale('al')

            this.$validator.validateAll().then(() => {
                this.$http.post('api/products', this.product)
                .then(response => {
                    this.$router.push('/feed')
                })
            })
        }
    }
}
</script>

<style>
    #createProduct {
        margin-top: 6%;
    }
</style>