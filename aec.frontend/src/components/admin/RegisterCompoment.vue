<script setup>
import { ref } from 'vue'
const firstName = ref('')
const lastName = ref('')
const date = ref('')
const gender = ref('')
const email = ref('')
const address = ref('')
const phone = ref('')
const nameLogin = ref('')
const password = ref('')
const passwordAccess = ref('')
</script>
<template>
  <div class="w-full h-screen flex box-border">
    <div class="w-1/2 h-full flex items-center justify-center">
      <el-scrollbar class="w-full">
        <div class="w-full bg-white p-4 space-y-6">
          <!--Title-->
          <div class="space-y-4 text-center font-bold items-center justify-center uppercase mt-10">
            <h3 class="text-black text-xl">Đăng ký</h3>
          </div>
          <div class="flex flex-col items-center justify-center">
            <div class="w-96 h-full">
              <!--Input-->
              <div class="text-sm font-semibold">
                <div class="flex space-x-4">
                  <div class="flex flex-col">
                    <label for="name">Họ</label>
                    <el-input v-model="firstName" name="name" class="!my-2 !w-46" />
                  </div>
                  <div class="flex flex-col">
                    <label for="name">Tên</label>
                    <el-input v-model="lastName" name="name" class="!my-2 !w-46" />
                  </div>
                </div>
                <div class="flex space-x-4">
                  <div class="flex flex-col">
                    <label for="name">Ngày sinh</label>
                    <el-input v-model="date" name="name" class="!my-2 !w-46" />
                  </div>
                  <div class="flex flex-col">
                    <label for="name">Giới tính</label>
                    <el-input v-model="gender" name="name" class="!my-2 !w-46" />
                  </div>
                </div>
                <label for="name">Email</label>
                <el-input v-model="email" name="name" class="!my-2" />
                <label for="name">Địa chỉ</label>
                <el-input v-model="address" name="name" class="!my-2" />
                <label for="name">Số điện thoại</label>
                <el-input v-model="phone" name="name" class="!my-2" />
                <label for="name">Tên đăng nhập</label>
                <el-input v-model="nameLogin" name="name" class="!my-2" />
                <label for="password">Mật khẩu</label>
                <el-input v-model="password" name="password" class="!my-2" show-password />
                <label for="password">Xác nhận mật khẩu</label>
                <el-input v-model="passwordAccess" name="password" class="!my-2" show-password />
              </div>
              <!--Button-->
              <div class="flex flex-col space-y-4">
                <div class="block w-full space-x-4">
                  <button
                    class="uppercase !text-xs !font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white p-2 w-full"
                  >
                    Đăng ký
                  </button>
                </div>
                <div class="block space-y-4">
                  <h3 class="text-center font-semibold">Nếu đã có tài khoản?</h3>
                  <router-link to="/">
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
      </el-scrollbar>
    </div>
    <div class="w-1/2 h-full">
      <img src="..." class="w-full h-full object-cover" />
    </div>
  </div>
</template>
