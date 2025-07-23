import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import api from '@/axios/api';
import { ElNotification, ElMessageBox } from 'element-plus'

export const useCategoryStore = defineStore('category', () => {
  const initModel = () => ({
    id: null,
    title: null,
    image: null
  });
  const categories = ref([]);
  const category = ref(initModel());
  const reLoaded = ref(false);
  const isLoading = ref(false);

  function getList() {
    isLoading.value = true;
    api.get('/categories')
      .then((response) => {
        categories.value = response.data;
        isLoading.value = false;
      })
      .catch(err => {
        isLoading.value = true;
        console.error("Error: ", err)
      });
  }

  function getById(id) {
    isLoading.value = true;
    api.get(`/categories/${id}`)
      .then((response) => {
        category.value = response.data;
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        console.error("Error: ", err);
      });
  }
  function resetForm() {
    category.value = initModel();
  }
  async function create(payload) {
    resetForm();
    isLoading.value = true;
    await api.post('/categories', payload)
      .then(() => {
        category.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã thêm thành công',
          type: 'success',
          position: 'top-right', // 👈 Vị trí góc trên phải
          duration: 3000,
          customClass: 'custom-success-notification',
        });
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        ElNotification({
          title: 'Thông báo',
          message: 'Lỗi: ' + err,
          type: 'danger',
          position: 'top-right', // 👈 Vị trí góc trên phải
          duration: 3000,
          customClass: 'custom-danger-notification',
        });
        console.error("Error: ", err);
      });
  }

  async function update(payload) {
    isLoading.value = true;
    await api.put(`/categories/${payload.id}`, payload)
      .then(() => {
        category.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã cập nhật thành công',
          type: 'success',
          position: 'top-right',
          duration: 3000,
          customClass: 'custom-success-notification',
        });
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        ElNotification({
          title: 'Thông báo',
          message: 'Lỗi: ' + err,
          type: 'danger',
          position: 'top-right',
          duration: 3000,
          customClass: 'custom-danger-notification',
        });
        console.error("Error: ", err);
      });
  }

  return {
    categories,
    category,
    reLoaded,
    isLoading,
    getList,
    getById,
    resetForm,
    create,
    update
  }
})
