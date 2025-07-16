<script setup lang="ts">
import { defineProps, watch, onMounted, computed } from 'vue'

const props = defineProps<{
  menuRef: any
}>()

const toggleMenu = () => {
  // console.log('=== DEBUG TOGGLE MENU ===')
  // console.log('1. props.menuRef:', props.menuRef)

  const menu = props.menuRef
  // console.log('2. menu object:', menu)

  // if (menu) {
  //   console.log('3. menu keys:', Object.keys(menu))
  //   console.log('4. toggleCollapse type:', typeof menu.toggleCollapse)
  //   console.log('5. toggleCollapse function:', menu.toggleCollapse)
  // }

  if (menu && typeof menu.toggleCollapse === 'function') {
    // console.log('6. Calling toggleCollapse...')
    menu.toggleCollapse()
  } else {
    // console.error('7. toggleCollapse not available!')
  }
}

// Debug watch
watch(
  () => props.menuRef,
  (val) => {
    console.log('Header - menuRef prop changed:', val)
  },
  { immediate: true },
)

onMounted(() => {
  console.log('Header mounted')
  console.log('Header - props.menuRef:', props.menuRef)
})
</script>

<template>
  <div class="flex justify-between items-center px-4 w-full h-full">
    <div>
      <button @click="toggleMenu" class="p-2 hover:bg-gray-200 rounded">
        <i class="fa-solid fa-bars"></i>
      </button>
    </div>
    <div class="flex gap-6 font-bold text-md">
      <span>Trang web chính</span>
      <span>Xin chào: Nguyễn Văn A</span>
      <span>Đăng xuất</span>
    </div>
  </div>
</template>
