<template>
    <div class="col-md-4">
        <div class="card" style="width: 18rem; height: 480px; margin-bottom: 5%;">
            <img :src="'http://localhost:8000/' + product.image" alt="" style="width: 60%;" class="card-img-top rounded mx-auto d-block">
            <div class="card-body">
                <h5 class="card-title"><router-link class="view" :to="'/products/' + product.id">{{ product.name }}</router-link></h5>
                <h5 class="card-title">{{ product.price }}</h5>
                <p class="card-text">Made by {{ product.user_id }}</p>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <a href="#" class="btn btn-outline-secondary1" style="margin-left: 8%;width: 40%;">
                        Wish list
                    </a>
                    <a href="#" class="btn btn-primary1" style="width: 40%;">
                        Buy
                    </a>
                </li>
            </ul>
            <div class="card-body" v-if="product.user_id == authenticatedUser.id">
                <a href="#" class="btn btn-danger" role="button" @click="$emit('delete-product')" style="margin-left: 8%;width: 40%;">
                        Delete
                    </a>
                <router-link class="btn btn-outline-info" :to="'/products/' + product.id + '/edit'" style="width: 40%;">
                        Edit
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: ['product', 'authenticatedUser'],
}
</script>

<style>
    .view {
        color: black;
    }

    .view:visited {
        color: black;
        text-decoration: none;
    }

    .view:hover {
        color: rgb(234,119,142);
        text-decoration: none;
    }
</style>