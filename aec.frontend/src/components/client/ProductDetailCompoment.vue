<script setup>
import { Minus, Plus } from '@element-plus/icons-vue'
import { ref, computed, onMounted } from 'vue'
import FormInfoStoreComponent from '../client/FormInfoStoreCompoment.vue'
import { useProductStore } from '@/stores/product'
import { storeToRefs } from 'pinia'
import { useRoute } from 'vue-router'

const productStore = useProductStore()
const { product, products } = storeToRefs(productStore)
const { getById } = productStore
const route = useRoute()

// /**
//  * @typedef {Object} Product
//  * @property {number} id
//  * @property {string} title
//  * @property {number} originalPrice
//  * @property {number} salePrice
//  * @property {string} image
//  * @property {string} [description]
//  */

// /**
//  * @typedef {Object} Props
//  * @property {Product} product
//  * @property {boolean} [showQuantity]
//  * @property {boolean} [showAddToCart]
//  */

// defineProps({
//   product: {
//     type: Object,
//     required: true,
//   },
//   showQuantity: {
//     type: Boolean,
//     default: false,
//   },
//   showAddToCart: {
//     type: Boolean,
//     default: false,
//   },
// })

const formRef = ref()
const loading = ref(true)
const error = ref(null)

const showForm = () => {
  if (formRef.value) {
    formRef.value.dialogFormVisible = true
  }
}
const num = ref(1)
const checkNum = computed(() => num.value > 1)

// Format price function with null check
const formatPrice = (price) => {
  if (!price || isNaN(price)) return '0'
  return price.toLocaleString('vi-VN')
}

// Computed properties for safe access
const productTitle = computed(() => product.value?.title || 'Đang tải...')
const productImage = computed(() => (product.value?.image ? product.value.image + '/main' : ''))
const productOriginalPrice = computed(() => product.value?.priceOriginal || 0)
const productSalePrice = computed(() => product.value?.priceOfficial || 0)
const productDetail = computed(() => product.value?.detail || '')

onMounted(async () => {
  try {
    loading.value = true
    error.value = null
    const id = route.params.id

    if (!id) {
      error.value = 'Không tìm thấy ID sản phẩm'
      return
    }

    // Nhận dữ liệu product detail từ id
    await getById(id)
  } catch (err) {
    console.error('Error loading product:', err)
    error.value = 'Có lỗi xảy ra khi tải thông tin sản phẩm'
  } finally {
    loading.value = false
  }
})
</script>
<template>
  <!-- Loading State -->
  <div
    v-if="loading"
    class="flex flex-col md:flex-row justify-center items-center gap-8 w-full my-6 px-2"
  >
    <div class="w-full md:w-1/2 flex justify-center items-center mb-4 md:mb-0">
      <div class="w-64 h-64 md:w-96 md:h-96 bg-gray-200 rounded-xl animate-pulse"></div>
    </div>
    <div class="w-full md:w-1/2 p-4 space-y-4">
      <div class="h-8 bg-gray-200 rounded animate-pulse"></div>
      <div class="h-6 bg-gray-200 rounded animate-pulse"></div>
      <div class="h-6 bg-gray-200 rounded animate-pulse"></div>
    </div>
  </div>

  <!-- Error State -->
  <div v-else-if="error" class="text-center py-8">
    <div class="text-red-500 text-lg">{{ error }}</div>
  </div>

  <!-- Product Detail -->
  <div
    v-else-if="product"
    class="flex flex-col md:flex-row justify-center items-center gap-8 w-full my-6 px-2"
  >
    <div class="w-full md:w-1/2 flex justify-center items-center mb-4 md:mb-0">
      <el-image
        class="w-64 h-64 md:w-96 md:h-96 rounded-xl shadow"
        :src="productImage"
        fit="cover"
        :preview-src-list="[productImage]"
      >
        <template #error>
          <div class="w-full h-full flex items-center justify-center bg-gray-100">
            <span class="text-gray-400">Không thể tải hình ảnh</span>
          </div>
        </template>
      </el-image>
    </div>
    <!-- Thông tin sản phẩm -->
    <div class="w-full md:w-1/2 p-4 space-y-4 flex flex-col justify-center">
      <!-- Tiêu đề -->
      <h2 class="text-2xl font-bold mb-2 capitalize">{{ productTitle }}</h2>
      <!-- Giá -->
      <div class="flex items-center gap-3">
        <span class="text-gray-400 text-base line-through font-medium"
          >{{ formatPrice(productOriginalPrice) }} <span class="underline">đ</span></span
        >
        <span class="text-[#FBA544] text-2xl font-bold"
          >{{ formatPrice(productSalePrice) }} <span class="underline">đ</span></span
        >
      </div>
      <!-- Chọn số lượng -->
      <div v-if="showQuantity" class="flex items-center space-x-2">
        <span class="font-semibold text-md mr-6">Số lượng</span>
        <div class="border-1 border-gray-300 rounded-lg p-1">
          <button
            :disabled="!checkNum"
            class="!font-extrabold !text-md !px-2 !border-0"
            @click="num--"
          >
            <el-icon><Minus /></el-icon>
          </button>
          <input
            v-model.number="num"
            class="w-12 text-center font-semibold text-lg rounded focus:border-[#fba544] focus:ring-1 focus:ring-[#fba544] outline-none"
          />
          <button class="!font-extrabold !text-md !px-2 !border-0" @click="num++">
            <el-icon><Plus /></el-icon>
          </button>
        </div>
      </div>
      <!--Button-->
      <div class="flex flex-col sm:flex-row gap-3 w-full mt-4">
        <button
          v-if="showAddToCart"
          class="w-full sm:w-1/2 text-sm font-semibold bg-black hover:bg-gray-700 transition text-white py-2 rounded-sm shadow"
        >
          Thêm vào giỏ hàng
        </button>
        <button
          class="w-full sm:w-2/3 text-sm font-semibold bg-[#fba544] hover:bg-[#ffba6b] transition text-white py-2 rounded-lg shadow"
          @click="showForm"
        >
          Mua ngay
        </button>
      </div>
    </div>
  </div>

  <!-- Product Description -->
  <div v-if="productDetail" class="w-full mt-8 px-2">
    <el-card>
      <p class="text item">{{ productDetail }}</p>
    </el-card>
  </div>

  <FormInfoStoreComponent ref="formRef" />
</template>

<style scoped></style>
