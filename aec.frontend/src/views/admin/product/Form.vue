<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElNotification } from 'element-plus'
import { required } from '@/utils/validators'
import { useCategoryStore } from '@/stores/category'
import { useProductStore } from '@/stores/product'
import { storeToRefs } from 'pinia'
import { useVuelidate } from '@vuelidate/core'
import { useRouter } from 'vue-router'
import QuillEditor from '@/components/QuillEditor.vue'

const router = useRouter()
const productStore = useProductStore()
const { product } = storeToRefs(productStore)
const categoryStore = useCategoryStore()
const { categories } = storeToRefs(categoryStore)
const rules = {
  id: { required },
  categoryId: { required },
  title: { required },
  detail: { required },
  image: { required },
  priceOriginal: { required },
  priceOfficial: { required },
  priceType: { required },
  status: { required },
  priorityLevel: { required },
  createdAt: { required },
  createdBy: { required },
  updatedAt: { required },
  updatedBy: { required },
  deletedAt: { required },
  deletedBy: { required },
}
const v$ = useVuelidate(rules, product)
function onHide() {
  productStore.resetForm()
  v$.value.$reset()
}
async function onSave() {
  const isFormCorrect = await v$.value.$validate()
  if (!isFormCorrect) return
  const payload = {
    ...product.value,
  }
  try {
    if (!product.value.id) {
      await productStore.create(payload)
    } else {
      await productStore.update(payload)
    }
    router.push('admin/product')
  } catch (error) {
    console.error('Error: ', error)
  }
}
onMounted(() => {
  categoryStore.getListByTen()
})
</script>
<template>
  <div class="mx-6">
    <div class="mb-4 flex justify-between items-center">
      <h3>Quản lý sản phẩm</h3>
      <el-button type="primary" @click="router.back()" plain>Quay lại</el-button>
    </div>
    <div class="w-full flex gap-4">
      <el-card class="!w-1/3">
        <el-form :model="product" :rules="rules" label-position="top">
          <!-- <el-form-item label="Mã sản phẩm" prop="id" label-postition="top">
            <el-input v-model="product.id" />
          </el-form-item> -->
          <el-form-item label="Danh mục" prop="title">
            <el-select v-model="product.categoryId" clearable placeholder="Chọn danh mục">
              <el-option
                v-for="item in categories"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Tiêu đề" prop="title">
            <el-input v-model="product.title" />
          </el-form-item>
          <el-form-item label="Hình ảnh" prop="image">
            <el-input v-model="product.image" />
          </el-form-item>
          <el-form-item label="Giá gốc" prop="priceOriginal">
            <el-input v-model="product.priceOriginal" />
          </el-form-item>
          <el-form-item label="Giá bán" prop="priceOfficial">
            <el-input v-model="product.priceOfficial" />
          </el-form-item>
          <el-form-item label="Giá gốc" prop="priceOriginal">
            <el-switch
              v-model="product.status"
              active-value="1"
              :inactive-value="0"
              active-text="Hoạt động"
              inactive-text="Không hoạt động"
              class="ml-2"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            />
          </el-form-item>
          <el-form-item label="Mức ưu tiên" prop="title">
            <el-select v-model="product.priorityLevel" clearable placeholder="Chọn mức ưu tiên">
              <el-option
                v-for="item in 20"
                :key="item"
                :label="'Mức ưu tiên số ' + item"
                :value="item"
              />
            </el-select>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card class="w-2/3">
        <div class="w-full my-4">
          <QuillEditor v-model="product.detail" />
        </div>
        <div class="w-full justify-between items-end flex">
          <el-button @click="onHide" class="!w-1/2">Đặt lại</el-button>
          <el-button type="primary" @click="onSave" class="!w-1/2"> Lưu lại </el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>
