<script setup>
import HeaderView from '@/components/client/HeaderView.vue'
</script>
<template>
  <div class="common-layout !w-full m-0 p-0 min-h-screen flex flex-col">
    <el-container class="!w-full m-0 p-0 flex-1 flex flex-col">
      <el-header class="sticky left-0 top-0 z-[1000] w-full !px-0">
        <HeaderView />
      </el-header>
      <el-main class="mt-4 flex-1">
        <router-view />
      </el-main>
      <el-footer
        class="w-full !px-0 bg-white border-t border-[#ffba6b] py-3 flex items-center justify-center"
      >
        <h2 class="text-center text-sm text-[#ffba6b] font-bold uppercase tracking-wide">
          Được thiết kế bởi đội ngũ kỹ thuật viên AEC
        </h2>
      </el-footer>
    </el-container>
  </div>
</template>
