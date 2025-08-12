<template>
  <el-menu
    default-active="2"
    class="el-menu-vertical-demo h-full"
    :collapse="isCollapse"
    @open="handleOpen"
    @close="handleClose"
  >
    <RouterLink to="/admin/main">
      <el-menu-item index="1">
        <el-icon><House /></el-icon>
        <template #title>Trang chủ</template>
      </el-menu-item>
    </RouterLink>
    <RouterLink to="/admin/dashbroad">
      <el-menu-item index="2">
        <el-icon><House /></el-icon>
        <template #title>Thống kê</template>
      </el-menu-item>
    </RouterLink>

    <el-sub-menu index="3">
      <template #title>
        <i class="fa-solid fa-shop ml-0.5"></i>
        <span class="ml-2">Sản phẩm</span>
      </template>
      <RouterLink to="/admin/category">
        <el-menu-item index="3-1">
          <template #title>
            <i class="fa-solid fa-list"></i>
            <span class="ml-2">Chuyên mục sản phẩm</span>
          </template>
        </el-menu-item>
      </RouterLink>
      <RouterLink to="/admin/product">
        <el-menu-item index="3-2">
        <template #title>
          <i class="fa-solid fa-list"></i>
          <span class="ml-2.5">Sản phẩm</span>
        </template>
      </el-menu-item>
      </RouterLink>
    </el-sub-menu>

    <RouterLink to="/admin">
      <el-menu-item index="4">
        <i class="fa-solid fa-box ml-0.5 text-lg"></i>
        <template #title>
          <span class="ml-2.5">Đơn hàng</span>
        </template>
      </el-menu-item>
    </RouterLink>
  </el-menu>
</template>

<script lang="ts" setup>
import { ref, defineExpose } from 'vue'
import { Document, Menu as IconMenu, Location, Setting, House } from '@element-plus/icons-vue'

const isCollapse = ref(false)

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

// Expose cả toggleCollapse và isCollapse để Layout có thể truy cập
defineExpose({
  toggleCollapse,
  isCollapse, // Expose trạng thái collapse
})
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 250px;
  min-height: 400px;
}

/* Style cho menu khi collapse */
.el-menu-vertical-demo.el-menu--collapse {
  width: 64px;
}
</style>
