<script setup>
import { Delete, Plus, Minus } from '@element-plus/icons-vue'
import { defineModel, ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const drawer = defineModel('drawer') // v-model:drawer

// Responsive drawer size
const cartDrawerSize = ref(window.innerWidth < 1024 ? '70%' : '40%')
const updateDrawerSize = () => {
  cartDrawerSize.value = window.innerWidth < 1024 ? '70%' : '40%'
}
onMounted(() => {
  window.addEventListener('resize', updateDrawerSize)
})
onUnmounted(() => {
  window.removeEventListener('resize', updateDrawerSize)
})

// Giả lập danh sách sản phẩm trong giỏ hàng
const cartItems = ref([
  {
    id: 1,
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_1__2_26.png',
    nane: 'Laptop MSI Modern 14 C12MO-660VN',
    priceOriginal: '15.000.000',
    price: '11.590.000',
    quantity: 1,
  },
  {
    id: 2,
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_659_4.png',
    nane: 'Laptop HP 15-FC0086AU A6VV9PA',
    priceOriginal: '15.190.000',
    price: '12.290.000',
    quantity: 1,
  },
  {
    id: 3,
    img: 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/r/group_658_2.png',
    nane: 'Laptop Lenovo IdeaPad Slim 5 14Q8X9 83HL000KVN',
    priceOriginal: '24.990.000',
    price: '19.490.000',
    quantity: 1,
  },
])

// Tổng tiền
const totalPrice = computed(() =>
  cartItems.value.reduce(
    (sum, item) => sum + Number((item.price + '').replace(/\./g, '')) * item.quantity,
    0,
  ),
)

// Xóa 1 sản phẩm
const removeItem = (id) => {
  cartItems.value = cartItems.value.filter((item) => item.id !== id)
}

// Xóa tất cả
const clearCart = () => {
  cartItems.value = []
}

// Tăng số lượng
const increaseQty = (item) => {
  item.quantity++
}

// Giảm số lượng
const decreaseQty = (item) => {
  if (item.quantity > 1) item.quantity--
}

// onClick
const onClick = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
    drawer.value = false
    router.push('/order')
  }, 1000)
}
</script>

<template>
  <el-drawer v-model="drawer" :size="cartDrawerSize">
    <template #header="{ titleId, titleClass }">
      <h4 :id="titleId" :class="titleClass" class="font-bold text-lg text-[#ffba6b]">Giỏ hàng</h4>
    </template>
    <div v-if="cartItems.length > 0" class="space-y-4">
      <div
        v-for="item in cartItems"
        :key="item.id"
        class="bg-white border border-[#ffba6b]/30 p-3 rounded-xl shadow flex flex-col lg:flex-row items-center lg:items-start gap-3 hover:shadow-lg transition-all w-full mx-auto"
      >
        <div class="mx-auto mb-2">
          <el-image style="width: 80px; height: 80px" :src="item.img" class="rounded-lg shadow" />
        </div>
        <div class="w-full flex flex-col items-center text-center lg:items-start lg:text-left">
          <div class="font-semibold text-black text-xs line-clamp-2 w-full">{{ item.nane }}</div>
          <div class="flex flex-col lg:flex-row items-center gap-1 mt-1 w-full">
            <span class="text-xs text-gray-400 line-through">{{ item.priceOriginal }} đồng</span>
            <span class="text-base font-bold text-[#ffba6b]">{{ item.price }} đồng</span>
          </div>
          <!-- Số lượng -->
          <div class="flex items-center justify-between w-full mt-2">
            <div class="flex items-center gap-2">
              <el-button
                @click="decreaseQty(item)"
                :icon="Minus"
                circle
                class="!bg-[#fff7ec] text-[#ffba6b] hover:!bg-[#ffba6b] hover:!text-white transition-all"
                size="small"
              />
              <span class="font-semibold w-6 text-center text-black">{{ item.quantity }}</span>
              <el-button
                @click="increaseQty(item)"
                :icon="Plus"
                circle
                class="!bg-[#fff7ec] text-[#ffba6b] hover:!bg-[#ffba6b] hover:!text-white transition-all"
                size="small"
              />
            </div>
            <button
              @click="removeItem(item.id)"
              class="mt-3 flex items-center justify-center w-9 h-9 rounded-full border-2 bg-red-50 hover:bg-red-500 hover:text-white hover:border-red-500 transition-all duration-200 group"
            >
              <Delete class="w-4 h-4 text-red-400 group-hover:text-white" />
            </button>
          </div>
        </div>
      </div>

      <div class="text-right font-bold mt-4 text-md md:text-lg text-[#ffba6b]">
        Tổng: {{ totalPrice.toLocaleString() }} đồng
      </div>

      <div class="text-right flex flex-col sm:flex-row gap-2 justify-end">
        <button
          class="text-xs font-semibold bg-black hover:bg-gray-700 duration-300 text-white p-2 w-full sm:w-36 rounded shadow"
          @click="clearCart"
        >
          Xoá tất cả
        </button>
        <button
          class="text-xs font-semibold bg-[#fba544] hover:bg-[#ffba6b] duration-300 text-white p-2 w-full sm:w-36 rounded shadow flex items-center justify-center"
          :disabled="loading"
          @click="onClick"
        >
          <span
            v-if="loading"
            class="animate-spin mr-2 w-4 h-4 border-2 border-white border-t-transparent rounded-full"
          ></span>
          {{ loading ? 'Đang mua hàng ...' : 'Mua hàng' }}
        </button>
      </div>
    </div>

    <div v-else class="flex flex-col items-center justify-center py-12">
      <el-empty
        image="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
        description="Giỏ hàng của bạn đang trống!"
        :image-size="120"
      />
    </div>
  </el-drawer>
</template>

<style scoped>
.el-drawer__body {
  padding: 1rem;
}
</style>
