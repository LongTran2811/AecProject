<script lang="ts" setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const input = ref()
const loading = ref(false)
const onClick = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
    router.push('/order')
  }, 1000)
}
</script>
<template>
  <div class="p-5 bg-none rounded-xl my-5 w-[100%] md:w-[80%]">
    <div class="max-w-2xl mx-auto">
      <div class="flex flex-col md:flex-row gap-3 items-center mb-4">
        <el-input
          v-model="input"
          class="flex-1"
          size="large"
          placeholder="Nhập từ khóa tìm kiếm..."
          :prefix-icon="Search"
          clearable
        />
        <el-button
          type="primary"
          class="!bg-[#fba544] !border-[#fba544] hover:!bg-[#ffba6b] hover:!border-[#ffba6b] text-white font-bold px-6 py-3 rounded-lg shadow-md transition-all duration-300 w-full md:w-auto flex items-center justify-center"
          size="large"
          :loading="loading"
          @click="onClick"
        >
          <el-icon class="mr-2">
            <Search />
          </el-icon>
          Tìm kiếm
        </el-button>
      </div>
      <!-- Popular search suggestions -->
      <div class="flex flex-col sm:flex-row items-start sm:items-center gap-2 sm:gap-3 flex-wrap">
        <span class="text-sm text-gray-600 font-medium">Tìm kiếm phổ biến:</span>
        <div class="flex gap-2 flex-wrap">
          <el-tag
            v-for="tag in ['Điện thoại', 'Laptop', 'Tai nghe', 'Đồng hồ']"
            :key="tag"
            class="cursor-pointer transition-all duration-300 rounded-md hover:scale-105 hover:!bg-[#ffba6b] hover:!text-white hover:!border-[#ffba6b] border border-[#ffba6b] text-[#ffba6b] bg-white"
            size="small"
            effect="plain"
            @click="input = tag"
            round
          >
            {{ tag }}
          </el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<!-- Đã loại bỏ toàn bộ CSS thuần, chỉ dùng Tailwind CSS -->
<style scoped>
.el-input {
  --el-color-primary: #fba544;
}
</style>
