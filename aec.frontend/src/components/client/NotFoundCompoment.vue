<template>
  <el-result title="Lỗi!!!" sub-title="Đường dẫn không tồn tại">
    <template #icon>
      <el-image
        src="/src/assets/img/404-error.png" style="width: 280px;"
      />
    </template>
    <template #extra>
      <router-link to="/">
              <button class="bg-[#fba544] hover:bg-[#ffba6b] transition px-6 py-2 rounded-lg font-semibold text-white shadow">Quay lại</button>
      </router-link>
    </template>
  </el-result>
</template>
