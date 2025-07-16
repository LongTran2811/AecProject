<script lang="ts" setup>
import { ElMessageBox } from 'element-plus'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})
const clickHandle = () => {
  dialogFormVisible.value = false;
   ElMessageBox.alert('Cảm ơn bạn đã mua hàng tại AEC. Chúng tôi đã tiếp nhận được đơn hàng của bạn và sẽ giao hàng đến bạn trong một vài ngày tới.', 'Tin nhắn', {
    // if you want to disable its autofocus
    // autofocus: false,
    confirmButtonText: 'Xem thêm sản phẩm khác',
    callback: () => {
      router.push('/store')
    },
  });
}
defineExpose({
  dialogFormVisible,
})
</script>
<template>
  <el-dialog v-model="dialogFormVisible" title="Thông tin khách hàng" width="500">
    <div class="my-4 space-y-2">
      <p>Sản phẩm: Dell Latitude 7440</p>
      <p>Giá: 4.000.000 <span class="underline">đ</span></p>
    </div>
    <el-form :model="form">
      <el-form-item label="Tên khách hàng" :label-width="formLabelWidth" required>
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Số điện thoại" :label-width="formLabelWidth" required>
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Email" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Quay lại</el-button>
        <el-button type="primary" @click="clickHandle"> Xác nhận </el-button>
      </div>
    </template>
  </el-dialog>
</template>
