<template>
    <div class="row">
        <div class="container" id="formId">
            <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input v-model="email" class="form-control" type="email" placeholder="Email"/>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input v-model="password" class="form-control" type="password" placeholder="Password"/>
            </div>
            <button @click="login" class="btn btn-primary1 pull-right btn-lg btn-block">Login</button>
            <!-- <pre>
                {{ $data }}
            </pre> -->
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            email: '',
            password: ''
        }
    },

    methods: {
        login () {

            var data = {
                client_id: 2,
                client_secret: 'N5Vbi4KKB1rK6u9Lxv0IJBnlViZiECAr3AE9IaZD',
                grant_type: 'password',
                username: this.email,
                password: this.password
            }

            this.$http.post("oauth/token", data)
            .then(response => {
                this.$auth.setToken(response.body.access_token, response.body.expires_in + Date.now())

                this.$router.push("/")
            })
        }
    }
}
</script>

<style>
    #formId {
        margin-top: 5%;
        /* margin-bottom: 30%; */
    }
</style>