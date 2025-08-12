<script setup>
import { useTimeStore } from '@/stores/time'

const timeStore = useTimeStore()
</script>
<template>
  <div>
    <el-card class="">
      <div class="w-full h-[500px] space-y-2">
        <p class="text-center font-bold text-3xl">Chào mừng</p>
        <p class="text-center font-semibold text-xl">Hôm nay</p>
        <div class="flex w-full gap-4 justify-center">
          <div class="flex items-center gap-3 py-2 px-4 bg-gray-200 rounded-md">
            <div class="text-xl">
              <i class="fa-solid fa-calendar"></i>
            </div>
            <div>
              <span class="text-xs">Ngày</span>
              <p class="font-bold text-md">{{ timeStore.currentDate }}</p>
            </div>
          </div>
          <div class="flex items-center gap-3 py-2 px-4 bg-gray-200 rounded-md">
            <div class="text-xl">
              <i class="fa-solid fa-clock"></i>
            </div>
            <div>
              <span class="text-xs">Giờ</span>
              <p class="font-bold text-md">{{ timeStore.currentTime }}</p>
            </div>
          </div>
        </div>
        <div class="text-center">Chúc bạn có một ngày làm việc thật hiệu quả !</div>
      </div>
    </el-card>
  </div>
</template>
<style scoped></style>
