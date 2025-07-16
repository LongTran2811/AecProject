<script setup>
import { ref } from 'vue'
const name = ref('')
const password = ref('')
</script>

<template>
  <div class="w-full min-h-full flex items-center justify-center px-2 mt-12 md:mt-8">
    <div class="w-full max-w-xs md:max-w-md bg-white p-4 md:p-8 rounded-lg shadow space-y-6">
      <!--Title-->
      <div class="space-y-4 text-center font-bold items-center justify-center uppercase">
        <h3 class="text-[#FBA544] text-2xl">Xin chào bạn</h3>
        <h3 class="text-black text-xl">Đăng nhập</h3>
      </div>
      <div class="flex flex-col items-center justify-center">
        <div class="w-full">
          <!--Input-->
          <div class="!space-y-4 text-sm font-semibold">
            <label for="name">Tên đăng nhập</label>
            <el-input v-model="name" name="name" class="!my-2 w-full" />
            <label for="password">Mật khẩu</label>
            <el-input v-model="password" name="password" class="!my-2 w-full" show-password />
          </div>
          <!--Button-->
          <div class="flex flex-col space-y-4 mt-4">
            <div
              class="block w-full space-y-2 md:space-x-4 md:space-y-0 md:flex md:justify-between"
            >
              <button
                class="uppercase !text-xs !font-bold bg-[#333333] hover:bg-[#5a5a5a] duration-300 text-white p-2 w-full md:w-44"
              >
                Quên mật khẩu
              </button>
              <button
                class="uppercase !text-xs !font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white p-2 w-full md:w-44 mt-2 md:mt-0"
              >
                Đăng nhập
              </button>
            </div>
            <div class="block space-y-4">
              <h3 class="text-center font-semibold">Nếu chưa có tài khoản?</h3>
              <RouterLink to="/register">
                <button
                  class="uppercase !text-xs !font-bold bg-[#333333] hover:bg-[#5a5a5a] duration-300 text-white p-2 w-full"
                >
                  Đăng ký
                </button>
              </RouterLink>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.el-input {
  --el-color-primary: #fba544;
}
</style>
