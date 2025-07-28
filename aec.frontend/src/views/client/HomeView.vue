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
const list = ref([
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_1__2_26.png',
    nane: 'Laptop MSI Modern 14 C12MO-660VN',
    priceOriginal: '15.000.000',
    price: '11.590.000',
  },
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_659_4.png',
    nane: 'Laptop HP 15-FC0086AU A6VV9PA',
    priceOriginal: '15.190.000',
    price: '12.290.000',
  },
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_658_2.png',
    nane: 'Laptop Lenovo IdeaPad Slim 5 14Q8X9 83HL000KVN',
    priceOriginal: '24.990.000',
    price: '19.490.000',
  },
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_1__2_26.png',
    nane: 'Laptop MSI Modern 14 C12MO-660VN',
    priceOriginal: '15.000.000',
    price: '11.590.000',
  },
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_659_4.png',
    nane: 'Laptop HP 15-FC0086AU A6VV9PA',
    priceOriginal: '15.190.000',
    price: '12.290.000',
  },
  {
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_658_2.png',
    nane: 'Laptop Lenovo IdeaPad Slim 5 14Q8X9 83HL000KVN',
    priceOriginal: '24.990.000',
    price: '19.490.000',
  },
])
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

