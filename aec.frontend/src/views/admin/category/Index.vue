<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { Search, CirclePlus, Delete, MoreFilled, Edit } from '@element-plus/icons-vue'
import { ElNotification, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { useCategoryStore } from '@/stores/category'
import { storeToRefs } from 'pinia'
import Create from './FormDialog.vue'

// const router = useRouter()
const input2 = ref('')
const categoryStore = useCategoryStore()
const { categories, isLoading } = storeToRefs(categoryStore)
const { getList } = categoryStore
//
onMounted(() => {
  getList()
})

const svg = `
        <path class="path" d="
          M 30 15
          L 28 17
          M 25.61 25.61
          A 15 15, 0, 0, 1, 15 30
          A 15 15, 0, 1, 1, 27.99 7.5
          L 15 15
        " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"/>
      `

// const emit = defineEmits(['show-alert'])

const remove = (row) => {
  ElMessageBox.confirm('Bạn có chắc muốn xóa bản ghi này?', 'Thông báo', {
    confirmButtonText: 'Có',
    cancelButtonText: 'Quay lại',
    type: 'warning',
  })
    .then(() => {
      ElNotification({
        title: 'Success',
        message: 'Delete completed',
        type: 'success',
        position: 'top-right', // 👈 Vị trí góc trên phải
        duration: 3000,
        customClass: 'custom-success-notification',
      })
    })
    .catch(() => {
      ElNotification({
        title: 'Cancelled',
        message: 'Delete canceled',
        type: 'info',
        position: 'top-right', // 👈 Vị trí góc trên phải
        duration: 3000,
        customClass: 'custom-secondary-notification',
      })
    })
}


// Handle Create
const formDialogRef = ref()
const openForm = async (row) => {
  if (row && row.id) {
    await categoryStore.getById(row.id)
  } else {
    categoryStore.resetForm()
  }
  formDialogRef.value?.openDialog()
}
</script>

<template>
  <div class="w-full text-left font-bold text-xl">
    <h3>Danh mục sản phẩm</h3>
  </div>
  <div class="w-full">
    <el-card class="">
      <div class="w-full justify-between items-center flex mb-4 px-2">
        <div>
          <el-input
            v-model="input2"
            style="width: 240px"
            placeholder="Tìm kiếm"
            :suffix-icon="Search"
          />
        </div>
        <div>
          <el-button type="danger" :icon="Delete" plain>Xóa đã chọn</el-button>
          <el-button
            class="!border-teal-500 hover:!bg-teal-500 !text-teal-500 hover:!text-white"
            type="success"
            :icon="CirclePlus"
            plain
            @click="openForm"
            >Thêm mới</el-button
          >
        </div>
      </div>
      <div>
        <el-table
          v-loading="isLoading"
          :element-loading-svg="svg"
          element-loading-text="Đang tải dữ liệu..."
          element-loading-svg-view-box="-10, -10, 50, 50"
          :data="categories"
          style="min-width: 100%"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="title" label="Tên danh mục" />
          <el-table-column label="Hình ảnh">
            <template #default="scope">
              <img :src="scope.row.image + '/thumb'" alt="Ảnh" class="w-16 h-16 object-cover rounded border" />
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="Tác vụ" min-width="10">
            <template #default="scope">
              <el-popover placement="bottom-start" trigger="click">
                <div class="flex flex-col items-start space-y-2">
                  <el-button
                    class="w-full !m-0 !justify-start"
                    text
                    @click="openForm(scope.row)"
                    :icon="Edit"
                    >Sửa</el-button
                  >
                  <el-button
                    class="w-full !m-0 !justify-start"
                    text
                    @click="remove(scope.row)"
                    :icon="Delete"
                    >Xoá</el-button
                  >
                </div>
                <template #reference>
                  <el-button type="secondary" size="small">
                    <el-icon><MoreFilled /></el-icon>
                  </el-button>
                </template>
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
  <Create ref="formDialogRef" />
</template>

<style>
@import url('@/assets/custom_notification.css');
</style>
