<script setup>
import { computed } from 'vue'

const props = defineProps({
  title: {
    type: String,
    default: '',
  },
  items: {
    type: Array,
    default: () => [],
  },
  loading: {
    type: Boolean,
    default: false,
  },
})

// Computed properties
const isEmpty = computed(() => !props.loading && (!props.items || props.items.length === 0))

// Format price function
const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN').format(price)
}
</script>

<template>
  <div class="w-full">
    <!-- Title Section -->
    <div class="text-center py-4">
      <h2 class="uppercase text-xl font-bold text-gray-800">{{ title }}</h2>
    </div>

    <!-- Loading State -->
    <div
      v-if="loading"
      class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4 py-4"
    >
      <div v-for="i in 5" :key="i" class="animate-pulse">
        <div class="w-full h-[260px] md:h-[330px] bg-gray-200 rounded-lg"></div>
      </div>
    </div>

    <!-- Empty State -->
    <div v-else-if="isEmpty" class="text-center py-8">
      <div class="text-gray-400 text-lg">Không có sản phẩm nào</div>
    </div>

    <!-- Products Grid -->
    <div
      v-else
      class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4 py-4 justify-center ipad-air-grid"
    >
      <RouterLink
        :to="{ name: 'detail', params: { id: item.id } }"
        v-for="(item, index) in items"
        :key="item.id || index"
        class="block group"
      >
        <el-card
          class="w-full h-[260px] md:h-[330px] relative mx-auto !rounded-lg hover:shadow-xl transition-all duration-300 cursor-pointer border-0 overflow-hidden group-hover:scale-105"
          shadow="hover"
        >
          <!-- Product Image -->
          <div class="w-full h-[100px] md:h-[200px] p-1 md:p-2 overflow-hidden">
            <img
              :src="item.img + '/main'"
              :alt="item.title"
              class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
              @error="$event.target.src = '/src/assets/img/404-error.png'"
            />
          </div>

          <!-- Product Title -->
          <div class="h-[56px] md:h-[70px] flex items-center px-2">
            <span
              class="font-bold text-xs md:text-sm line-clamp-2 text-gray-800 group-hover:text-[#FBA544] transition-colors"
            >
              {{ item.title }}
            </span>
          </div>

          <!-- Product Prices -->
          <div class="absolute bottom-2 left-2 px-2 w-full">
            <p class="text-gray-400 text-xs md:text-sm line-through">
              {{ formatPrice(item.priceOriginal) }}
              <span class="underline">đ</span>
            </p>
            <p class="text-[#FBA544] text-sm md:text-base font-bold">
              {{ formatPrice(item.priceOfficial) }}
              <span class="underline">đ</span>
            </p>
          </div>

          <!-- Discount Badge -->
          <div
            v-if="item.priceOriginal > item.priceOfficial"
            class="absolute top-2 right-2 bg-red-500 text-white text-xs px-2 py-1 rounded-full font-bold"
          >
            -{{
              Math.round(((item.priceOriginal - item.priceOfficial) / item.priceOriginal) * 100)
            }}%
          </div>
        </el-card>
      </RouterLink>
    </div>
  </div>
</template>

<style scoped>
@media (min-width: 820px) and (max-width: 1023px) {
  .ipad-air-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

/* Custom scrollbar for better UX */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background: #fba544;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #ffba6b;
}
</style>
