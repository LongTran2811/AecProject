<!-- CategoryFormDialog.vue -->
<script lang="ts" setup>
import { computed, ref } from 'vue'
import { required } from '@/utils/validators'
import { useCategoryStore } from '@/stores/category'
import { storeToRefs } from 'pinia'
import { useVuelidate } from '@vuelidate/core'

const categoryStore = useCategoryStore()
const { category } = storeToRefs(categoryStore)
const centerDialogVisible = ref(false)
const title = computed(() => {
  return category.value.id ? 'Cập nhật danh mục ' + category.value.id : 'Thêm mới danh mục'
})

const rules = {
  title: { required },
  image: { required },
}
const v$ = useVuelidate(rules, category)

function onHide() {
  categoryStore.resetForm()
  v$.value.$reset()
}

async function onSave() {
  const isFormCorrect = await v$.value.$validate()
  if (!isFormCorrect) return
  const payload = {
    ...category.value,
  }

  try {
    if (!category.value.id) {
      await categoryStore.create(payload)
    } else {
      await categoryStore.update(payload)
    }
    centerDialogVisible.value = false
    categoryStore.getList()
    // emit event nếu cha cần
    // emit('success')
  } catch (error) {
    console.error('Error: ', error)
  }
}

defineExpose({
  openDialog: () => {
    centerDialogVisible.value = true
  },
})
</script>

<template>
  <el-dialog v-model="centerDialogVisible" :title="title" width="500" align-center>
    <el-form :model="category" :rules="rules" label-position="top">
      <el-form-item label="Tiêu đề" prop="title">
        <el-input v-model="category.title" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Hình ảnh" prop="image">
        <el-input v-model="category.image" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="onHide">Đặt lại</el-button>
        <el-button type="primary" @click="onSave"> Lưu </el-button>
      </div>
    </template>
  </el-dialog>
</template>
