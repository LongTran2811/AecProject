<script setup>
// import { ref } from 'vue'

defineProps({
  title: {
    type: String,
    default: '',
  },
  items: {
    type: Array,
    default: () => [],
  },
})

</script>

<template>
  <div class="w-full">
    <div class="text-center py-2">
      <b class="uppercase text-xl">{{ title }}</b>
    </div>
    <RouterLink to="/detail">
      <div
        class="grid grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-4 py-4 justify-center ipad-air-grid"
      >
        <el-card
          class="w-full h-[260px] md:h-[330px] relative mx-auto !rounded-lg"
          v-for="(item, index) in items"
          :key="index"
        >
          <div class="w-full h-[100px] md:h-[200px] p-1 md:p-2">
            <img :src="item.img" :alt="item.nane" class="w-full h-full object-cover" />
          </div>
          <div class="h-[56px] md:h-[70px] flex items-center">
            <span class="font-bold text-xs md:text-md line-clamp-2">{{ item.nane }}</span>
          </div>
          <div>
            <div class="absolute bottom-2 left-2 px-2">
              <p class="text-gray-400 text-xs md:text-sm line-through ">
                {{ item.priceOriginal }} <span class="underline">đ</span>
              </p>
              <p class="text-[#FBA544] text-md md:text-md font-bold">{{ item.price }} <span class="underline">đ</span></p>
            </div>
          </div>
        </el-card>
      </div>
    </RouterLink>
  </div>
</template>

<style scoped>
@media (min-width: 820px) and (max-width: 1023px) {
  .ipad-air-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}
</style>
