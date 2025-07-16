<script setup lang="ts">
import { RouterLink } from 'vue-router'
import ShoppingCartCompoment from './ShoppingCartCompoment.vue'
import { ref } from 'vue'
const drawer = ref(false)
const showMenu = ref(false)
</script>

<template>
  <!-- Thanh trên cùng -->
  <div class="bg-white text-black gap-4 text-xs font-bold uppercase flex justify-between py-0 px-4">
    <div class="hover:bg-[#f5ad5b] duration-400 hover:text-white h-full px-2 py-2">
      <span>Tải ứng dụng</span>
    </div>
    <div class="flex gap-4">
      <span class="hover:bg-[#f5ad5b] duration-400 hover:text-white px-2 py-2">Ngôn ngữ</span>
      <span class="hover:bg-[#f5ad5b] duration-400 hover:text-white px-2 py-2"
        ><RouterLink to="/login">Đăng nhập</RouterLink></span
      >
    </div>
  </div>
  <!-- Header chính -->
  <div
    class="w-full bg-[#FBA544] px-2 md:px-5 flex flex-col md:flex-row text-white text-xs font-semibold justify-between items-center !h-auto md:!h-[50px]"
  >
    <div class="flex w-full md:w-auto justify-between items-center py-2 md:py-0">
      <RouterLink to="/">
        <img src="/src/assets/img/logo_aec.png" alt="" class="w-[40px] md:w-[50px]" />
      </RouterLink>
      <!-- Hamburger menu cho mobile -->
      <button class="md:hidden text-white text-2xl px-2" @click="showMenu = !showMenu">
        <i class="fa-solid" :class="showMenu ? 'fa-xmark' : 'fa-bars'"></i>
      </button>
    </div>
    <!-- Menu -->
    <ul
      class="flex-col md:flex-row flex md:flex gap-0 md:gap-6 w-full md:w-auto text-center uppercase md:text-left bg-[#FBA544] md:bg-transparent transition-all duration-300"
      :class="[
        showMenu ? 'flex' : 'hidden',
        'md:flex',
        'z-50',
        'md:items-center',
        'md:static',
        'absolute left-0 top-[75px] md:top-0',
      ]"
    >
      <RouterLink to="/">
        <li
          class="flex gap-2 items-center hover:bg-[#a06d32] duration-400 py-3 px-2 md:py-4 md:px-2 justify-center md:justify-start"
        >
          <i class="fa-solid fa-house" style="color: #ffffff; font-size: 17px"></i>
          <span>Trang chủ</span>
        </li>
      </RouterLink>
      <RouterLink to="/store">
        <li
          class="flex gap-2 items-center hover:bg-[#a06d32] duration-400 py-3 px-2 md:py-4 md:px-2 justify-center md:justify-start"
        >
          <i class="fa-solid fa-store" style="color: #ffffff; font-size: 17px"></i>
          <span>Mua sắm</span>
        </li>
      </RouterLink>
      <li
        class="flex gap-2 items-center hover:bg-[#a06d32] duration-400 py-3 px-2 md:py-4 md:px-2 justify-center md:justify-start"
      >
        <i class="fa-solid fa-headset" style="color: #ffffff; font-size: 17px"></i>
        <span>Hỗ trợ kỹ thuật</span>
      </li>
      <li
        class="flex gap-2 items-center hover:bg-[#a06d32] duration-400 py-3 px-2 md:py-4 md:px-2 justify-center md:justify-start"
      >
        <i class="fa-solid fa-building" style="color: #ffffff; font-size: 17px"></i>
        <span>Giới thiệu</span>
      </li>
      <li
        class="flex gap-2 items-center hover:bg-[#a06d32] duration-400 py-3 px-2 md:py-4 md:px-2 justify-center md:justify-start"
      >
        <i class="fa-solid fa-phone" style="color: #ffffff; font-size: 17px"></i>
        <span>Liên hệ</span>
      </li>
    </ul>
    <!-- Giỏ hàng -->
    <div
      class="flex gap-4 items-center hover:bg-[#a06d32] duration-400 py-3 px-4 md:py-4 md:px-4 cursor-pointer"
      @click="drawer = true"
    >
      <i class="fa-solid fa-cart-shopping" style="color: #ffffff; font-size: 17px"></i>
      <ShoppingCartCompoment v-model:drawer="drawer" />
    </div>
  </div>
</template>
