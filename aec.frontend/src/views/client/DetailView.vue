<script setup>
import ProductCompoment from '@/components/client/ProductCompoment.vue'
import ProductDetailCompoment from '@/components/client/ProductDetailCompoment.vue'
import { onMounted, ref } from 'vue'
import { useProductStore } from '@/stores/product'
import { storeToRefs } from 'pinia'

const productStore = useProductStore()
const { products } = storeToRefs(productStore)

onMounted(async () => {
  await productStore.getListByLevel()
})
</script>
<template>
  <div>
    <ProductDetailCompoment :product="product" :show-quantity="true" :show-add-to-cart="true" />
  </div>
  <div class="my-4">
    <ProductCompoment title="Các sản phẩm khác" :items="products" />
  </div>
</template>

<style scoped></style>
