<script setup>
import { ref } from 'vue'
const name = ref('')
const password = ref('')
</script>

<template>
  <div class="w-full min-h-screen flex items-center justify-center px-2 mt-12 md:mt-8">
    <div class="w-full max-w-xs md:max-w-lg bg-white p-4 md:p-8 rounded-lg shadow space-y-6">
      <!--Title-->
      <div class="space-y-4 text-center items-center justify-center uppercase">
        <h3 class="text-black font-bold text-xl">Đăng ký</h3>
      </div>
      <div class="flex flex-col items-center justify-center">
        <div class="w-full h-full">
          <!--Input-->
          <div class="text-sm font-semibold">
            <div class="flex space-x-4">
              <div class="flex flex-col w-full">
                <label for="name">Họ</label>
                <el-input v-model="name" name="name" class="!my-2 w-full" />
              </div>
              <div class="flex flex-col w-full">
                <label for="name">Tên</label>
                <el-input v-model="name" name="name" class="!my-2 w-full" />
              </div>
            </div>
            <div class="flex space-x-4">
              <div class="flex flex-col w-full">
                <label for="name">Ngày sinh</label>
                <el-input v-model="name" name="name" class="!my-2 w-full" />
              </div>
              <div class="flex flex-col w-full">
                <label for="name">Giới tính</label>
                <el-input v-model="name" name="name" class="!my-2 w-full" />
              </div>
            </div>
            <label for="name">Email</label>
            <el-input v-model="name" name="name" class="!my-2 w-full" />
            <label for="name">Địa chỉ</label>
            <el-input v-model="name" name="name" class="!my-2 w-full" />
            <label for="name">Số điện thoại</label>
            <el-input v-model="name" name="name" class="!my-2 w-full" />
            <label for="name">Tên đăng nhập</label>
            <el-input v-model="name" name="name" class="!my-2 w-full" />
            <label for="password">Mật khẩu</label>
            <el-input v-model="password" name="password" class="!my-2 w-full" show-password />
            <label for="password">Xác nhận mật khẩu</label>
            <el-input v-model="password" name="password" class="!my-2 w-full" show-password />
          </div>
          <!--Button-->
          <div class="flex flex-col space-y-4 mt-4">
            <div class="block w-full space-x-4">
              <button
                class="uppercase !text-xs !font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white p-2 w-full"
              >
                Đăng ký
              </button>
            </div>
            <div class="block space-y-4">
              <h6 class="text-center font-semibold">Nếu đã có tài khoản?</h6>
              <router-link to="/login">
                <button
                  class="uppercase !text-xs !font-bold bg-[#333333] hover:bg-[#5a5a5a] duration-300 text-white p-2 w-full"
                >
                  Đăng nhập
                </button>
              </router-link>
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
