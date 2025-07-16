<script lang="ts" setup>
import { Minus, Plus } from '@element-plus/icons-vue'
import { ref, computed } from 'vue'
import FormInfoStoreComponent from '../client/FormInfoStoreCompoment.vue'

const formRef = ref<InstanceType<typeof FormInfoStoreComponent>>()
const showForm = () => {
  if (formRef.value) {
    formRef.value.dialogFormVisible = true
  }
}
const num = ref(1)
const checkNum = computed(() => num.value > 1)
</script>
<template>
  <div class="flex flex-col md:flex-row justify-center items-center gap-8 w-full my-6 px-2">
    <div class="w-full md:w-1/2 flex justify-center items-center mb-4 md:mb-0">
      <el-image
        class="w-64 h-64 md:w-96 md:h-96 rounded-xl shadow"
        src="https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/t/e/text_ng_n_1__2_26.png"
        fit="cover"
      ></el-image>
    </div>
    <!-- Thông tin sản phẩm -->
    <div class="w-full md:w-1/2 p-4 space-y-4 flex flex-col justify-center">
      <!-- Tiêu đề -->
      <h2 class="text-2xl font-bold mb-2 capitalize">Tiêu đề sản phẩm</h2>
      <!-- Giá -->
      <div class="flex items-center gap-3">
        <span class="text-gray-400 text-base line-through font-medium">000.000 <span class="underline">đ</span></span>
        <span class="text-[#FBA544] text-2xl font-bold">000.000 <span class="underline">đ</span></span>
      </div>
      <!-- Chọn số lượng -->
      <!-- <div class="flex items-center space-x-2">
        <span class="font-semibold text-md mr-6">Số lượng</span>
        <div class="border-1 border-gray-300 rounded-lg p-1">
          <button
            :disabled="!checkNum"
            class="!font-extrabold !text-md !px-2 !border-0"
            @click="num--"
          >
            <el-icon><Minus /></el-icon>
          </button>
          <input
            v-model.number="num"
            class="w-12 text-center font-semibold text-lg rounded focus:border-[#fba544] focus:ring-1 focus:ring-[#fba544] outline-none"
          />
          <button class="!font-extrabold !text-md !px-2 !border-0" @click="num++">
            <el-icon><Plus /></el-icon>
          </button>
        </div>
      </div> -->
      <!--Button-->
      <!-- <div class="flex flex-col sm:flex-row gap-3 w-full mt-4">
        <button
          class="w-full sm:w-1/2 text-sm font-semibold bg-black hover:bg-gray-700 transition text-white py-2 rounded-sm shadow"
        >
          Thêm vào giỏ hàng
        </button>
        <button
          class="w-full sm:w-1/2 text-sm font-semibold bg-[#fba544] hover:bg-[#ffba6b] transition text-white py-2 rounded-lg shadow"
        >
          Mua ngay
        </button>
      </div> -->
      <div class="flex flex-col sm:flex-row gap-3 w-full mt-4">
        <button
          class="w-full sm:w-2/3 text-sm font-semibold bg-[#fba544] hover:bg-[#ffba6b] transition text-white py-2 rounded-lg shadow"
          @click="showForm"
        >
          Mua ngay
        </button>
      </div>
    </div>
  </div>
  <div class="w-full mt-8 px-2">
    <el-card>
      <p v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</p>
    </el-card>
  </div>
    <FormInfoStoreComponent ref="formRef" />
</template>

<style scoped></style>
