<script setup>
import TimedCarouselComponent from './TimedCarouselComponent.vue';

const items = [
  {
    id: 1,
    name: 'Sản phẩm 1',
  },
];
</script>
<template>
  <el-card class="w-full h-ful container">
    <div>
      <h1>Tiêu đề sự kiện</h1>
      <p>Ngày: 12/07/2025</p>
    </div>
    <div>
    <!-- <img src="https://picsum.photos/200/300" alt="event" /> -->
     <div>Nội dung sự kiện</div>
    </div>
    <div>
      <TimedCarouselComponent  :items="items" />
    </div>
  </el-card>
</template>
