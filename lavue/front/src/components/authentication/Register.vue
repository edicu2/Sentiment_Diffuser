<template>
    <div class="row">
        <div class="container" id="formRegister">
            <form>
                <div class="form-group">
                    <label for="exampleInputName">Name</label>
                    <input v-model="name" class="form-control" type="text" placeholder="Name"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail1">Email address</label>
                    <input v-model="email" class="form-control" type="email" aria-describedby="emailHelp" placeholder="Email"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password</label>
                    <input v-model="password" class="form-control" type="password" placeholder="Password"/>
                </div>
                <button class="btn btn-primary1 pull-right btn-lg btn-block">Register</button>
            </form>
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
            name: '',
            email: '',
            password: ''        
        }
    }
}
</script>

<style>
    #formRegister {
        margin-top: 5%;
    }
</style>