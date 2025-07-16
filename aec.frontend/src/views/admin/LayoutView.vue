<script lang="ts" setup>
import HeaderCompoment from '@/components/admin/HeaderCompoment.vue'
import MenuCompoment from '@/components/admin/MenuCompoment.vue'
import { ref, computed } from 'vue'

const menuComponent = ref()

// Tạo computed để đảm bảo reactivity
const menuRef = computed(() => menuComponent.value)

// Computed để tính width của aside dựa trên trạng thái collapse
const asideWidth = computed(() => {
  // Kiểm tra nếu menu component đã sẵn sàng và có isCollapse
  if (menuComponent.value && menuComponent.value.isCollapse !== undefined) {
    return menuComponent.value.isCollapse ? '64px' : '250px'
  }
  return '250px' // Default width
})
</script>

<template>
  <div class="common-layout bg-gray-100 w-full h-full">
    <el-container>
      <!-- Menu với width động -->
      <el-aside :width="asideWidth">
        <MenuCompoment ref="menuComponent" />
      </el-aside>

      <!-- Content area -->
      <el-container>
        <el-header class="h-30">
          <HeaderCompoment :menu-ref="menuRef" />
        </el-header>
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
