<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
const handleLoginClick = () => {
  router.push('/admin/dashbroad')
}
const handleRegisterClick = () => {
  router.push('/register')
}
</script>
<template>
  <div class="w-full h-screen flex">
    <div class="w-1/2 h-full">
      <img src="..." class="w-full h-full object-cover" />
    </div>
    <div class="w-1/2 h-full flex items-center justify-center">
      <div class="w-full h-full bg-white p-4 space-y-6 flex items-center justify-center">
        <div class="w-96 space-y-4">
          <!--Title-->
          <div class="text-center uppercase space-y-2">
            <h3 class="text-[#FBA544] font-bold text-2xl">Xin chào bạn</h3>
            <h3 class="text-black font-bold text-xl">Đăng nhập</h3>
          </div>
          <!--Input-->
          <div class="space-y-4 text-sm font-semibold">
            <label for="name">Tên đăng nhập</label>
            <el-input v-model="name" name="name" class="!my-2" />
            <label for="password">Mật khẩu</label>
            <el-input v-model="password" name="password" class="!my-2" show-password />
          </div>
          <!--Button-->
          <div class="flex flex-col space-y-4">
            <div class="flex justify-between">
              <button
                class="uppercase text-xs font-bold bg-[#333333] hover:bg-[#5a5a5a] duration-300 text-white p-2 w-[48%]"
              >
                Quên mật khẩu
              </button>
              <button
                class="uppercase text-xs font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white p-2 w-[48%]"
                @click="handleLoginClick"
              >
                Đăng nhập
              </button>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
