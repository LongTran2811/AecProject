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
        categories.value = response.data.data;
        isLoading.value = false;
      })
      .catch(err => {
        isLoading.value = true;
        console.error("Error: ", err)
      });
  }
  function getListByTen() {
    isLoading.value = true;
    api.get('/categories')
      .then((response) => {
        categories.value = response.data.data.map(x => ({
          label: `${x.title}`,
          value: x.id
        }));
        isLoading.value = false;
      })
      .catch(err => {
        isLoading.value = true;
        console.error("Error: ", err)
      });
  }

  function getById(id) {
    isLoading.value = true;
    api.get(`/categories/getId/${id}`)
      .then((response) => {
        category.value = response.data;
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        console.error("Error: ", err);
      });
  }
  async function create(payload) {
    resetForm();
    isLoading.value = true;
    await api.post('/categories/create', payload)
      .then(() => {
        category.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã thêm thành công',
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
          position: 'top-right', // 👈 Vị trí góc trên phải
          duration: 3000,
          customClass: 'custom-danger-notification',
        });
        console.error("Error: ", err);
      });
  }

  async function update(payload) {
    isLoading.value = true;
    await api.put(`/categories/update/${payload.id}`, payload)
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
  async function remove(id) {
    isLoading.value = true;
    await api.delete(`/categories/delete/${id}`)
      .then(() => {
        category.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã xoá thành công',
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
  async function removes(ids) {
    isLoading.value = true;
    // console.log('Sending IDs for deletion:', ids); // Debug log
    await api.put('/categories/removes', {ids:ids})
      .then(() => {
        ElNotification({
          title: 'Thông báo',
          message: 'Đã xoá thành công',
          type: 'success',
          position: 'top-right',
          duration: 3000,
          customClass: 'custom-success-notification',
        });
        isLoading.value = false;
        getList();
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
  function resetForm() {
    category.value = initModel();
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
    update,
    remove,
    removes,
    getListByTen
  }
})
