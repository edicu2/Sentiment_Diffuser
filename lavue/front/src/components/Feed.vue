<template>
    <div class="container" id="feed">
        <h2>Shop-Aroma Oils</h2>
        <hr>
        <br>
        <my-products></my-products>
    </div>
</template>

<script>
import Products from './product/Products.vue'

export default {
    components: {
        'my-products': Products
    }
}
</script>

<style>
    #feed {
        padding-top: 6%;
    }
</style>