<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top" style="opacity: 0.9;">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-center" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="navbar-brand" href="#">
                        <!-- <img src="/img/logo.png" width="100" height="30" class="d-inline-block align-top" alt=""> -->
                        <img src="/src/img/logo1-7.png" width="150" class="d-inline-block align-top" alt="">
                    </a>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/">
                      <a>Home</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/login" v-if="! isAuth">
                      <a>Login</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/register" v-if="! isAuth">
                      <a>Register</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/feed" v-if="isAuth">
                      <a>Shop</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/products/create" v-if="isAuth">
                      <a>Create_Product</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/products/create" v-if="isAuth">
                      <a>Community</a>
                    </router-link>
                </li>
                <li class="nav-item active">
                    <router-link class="nav-link" to="/logout" v-if="isAuth">
                      <a>Logout</a>
                    </router-link>
                </li>
            </ul>
        </div>
    </nav>
  </div>
</template>

<script>
export default {
    data () {
      return {
        isAuth: null
      }
    },

    created () {
      this.isAuth = this.$auth.isAuthenticated()

      this.setAuthenticatedUser()
    },

    methods: {
      setAuthenticatedUser () {
        this.$http.get('api/user')
        .then(response => {
          this.$auth.setAuthenticatedUser(response.body)

          console.log(this.$auth.getAuthenticatedUser())
        })
      }
    }
}
</script>

<style>
    .router-link-active {
      background-color: blue;
      color: white;
    }
</style>