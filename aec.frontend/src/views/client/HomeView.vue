<script setup>
import CarouselCompoment from '@/components/client/CarouselCompoment.vue';
import ProductCompoment from '@/components/client/ProductCompoment.vue';
import { ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';
import { storeToRefs } from 'pinia';
const productStore = useProductStore();
const {products} = storeToRefs(productStore);
const {getListByLevel} = productStore;
onMounted(() => {
  getListByLevel();
})
</script>
<template>
  <div>
    <div class="">
    <CarouselCompoment />
  </div>
  <div>
    <ProductCompoment title="Sản phẩm nổi bật" :items="products"/>
  </div>
  </div>
</template>

<style scoped>

</style>

