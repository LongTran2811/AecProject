<script setup lang="ts">
//Select
// import { reactive, ref } from 'vue';
// import FESelect from '@/components/FESelect.vue';
// import { useCategoryStore } from '@/stores/category';

// const categoryStore = useCategoryStore();
// const form = reactive({ categoryId: '' });
// const submitted = ref(false);

// // callback cho FESelect
// const loadCategoryOptions = async () => {
//   await categoryStore.fetchCategories();
//   return categoryStore.categories.map(c => ({
//     label: c.name,
//     value: c.id
//   }));
// };

//MultiSelect
// import FEMulSelect from '@/components/FEMulSelect.vue';
// import { useCategoryStore } from '@/stores/category';
// import { reactive, ref } from 'vue';

// const categoryStore = useCategoryStore();
// const submitted = ref(false);

// // form cho multiple => mảng
// const form = reactive({
//   categoryIds: [] as number[]
// });

// const loadCategoryOptions = async () => {
//   await categoryStore.fetchCategories();
//   return categoryStore.categories.map(c => ({
//     label: c.name,
//     value: c.id
//   }));
// };
</script>

<template>
  <div>
    <!--Input-->
    <!-- Icon bên trái -->
    <!-- <FEInput
  v-model="form.username"
  id="username"
  placeHolder="Nhập tên đăng nhập"
  icon="fa-solid fa-user"
  iconPosition="left"
  :rules="{ required: true, message: 'Tên không được để trống' }"
  :submitted="submitted"
/> -->

    <!-- Icon bên phải -->
    <!-- <FEInput
  v-model="form.password"
  id="password"
  placeHolder="Nhập mật khẩu"
  icon="fa-solid fa-eye"
  iconPosition="right"
  :rules="{ required: true, message: 'Mật khẩu không được để trống' }"
  :submitted="submitted"
/> -->

    <!--Textarea-->

    <!--Trường hợp 1: Textarea cố định số dòng-->
    <!-- <FETextarea
  v-model="description"
  id="desc"
  :rows="5"
  :auto-resize="false"
  place-holder="Nhập mô tả..."
/> -->

    <!--Trường hợp 2: Textarea tự động co giãn, với số dòng tối thiểu-->
    <!-- <FETextarea
  v-model="comment"
  id="comment"
  :rows="3"
  :auto-resize="true"
  place-holder="Nhập bình luận..."
/> -->

    <!--Trường hợp 3: Tùy chỉnh bằng className-->
    <!-- <FETextarea
  v-model="note"
  id="note"
  :rows="2"
  :auto-resize="true"
  place-holder="Ghi chú"
  class-name="w-full border rounded-md"
/> -->

    <!--Select-->

    <!-- <FESelect
    id="category"
    v-model="form.categoryId"
    placeHolder="Chọn danh mục"
    :load-options="loadCategoryOptions"
    :rules="{ required: true, message: 'Phải chọn danh mục' }"
    :submitted="submitted"
  /> -->

  <!--MultiSelect-->
  <!-- <FEMulSelect
    id="categories"
    v-model="form.categoryIds"
    placeHolder="Chọn nhiều danh mục"
    :load-options="loadCategoryOptions"
    :rules="{ required: true, message: 'Phải chọn ít nhất 1 danh mục' }"
    :submitted="submitted"
  /> -->
  </div>
</template>
