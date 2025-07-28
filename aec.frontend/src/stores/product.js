import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import api from '@/axios/api';
import { ElNotification, ElMessageBox } from 'element-plus'

export const useProductStore = defineStore('product', () => {
  const initModel = () => ({
    id: null,
    categoryId: null,
    title: null,
    detail: null,
    image: null,
    priceOriginal: null,
    priceOfficial: null,
    priceType: null,
    status: null,
    priorityLevel: null,
    createdAt: null,
    createdBy: null,
    updatedAt: null,
    updatedBy: null,
    deletedAt: null,
    deletedBy: null,
  });
  const products = ref([]);
  const product = ref(initModel());
  const reLoaded = ref(false);
  const isLoading = ref(false);

  function getList() {
    isLoading.value = true;
    api.get('/products')
      .then((response) => {
        products.value = response.data;
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        console.error("Error: ", err);
      });
  };
  function getListByLevel() {
    isLoading.value = true;
    api.get('/products/level')
      .then((response) => {
        products.value = response.data;
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        console.error("Error: ", err);
      });
  };
  function getById(id) {
    isLoading.value = true;
    api.get(`/products/getId/${id}`)
      .then((response) => {
        product.value = response.data;
        isLoading.value = false;
      })
      .catch((err) => {
        isLoading.value = true;
        console.error("Error: ", err);
      });
  }
  async function create(payload) {
    isLoading.value = true;
    await api.post('/products/create', payload)
      .then(() => {
        product.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã thêm thành công',
          type: 'success',
          position: 'top-right',
          duration: 3000,
          customClass: 'custom-success-notification',
        });
        isLoading.value = false;
      }).catch((err) => {
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
  async function update(payload) {
    resetForm();
    isLoading.value = true;
    await api.put(`/products/update/${payload.id}`, payload)
      .then(() => {
        product.value = initModel();
        ElNotification({
          title: 'Thông báo',
          message: 'Đã cập nhật thành công',
          type: 'success',
          position: 'top-right',
          duration: 3000,
          customClass: 'custom-success-notification',
        });
        isLoading.value = false;
      }).catch((err) => {
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
    await api.delete(`/products/delete/${id}`)
      .then(() => {
        product.value = initModel();
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

    await api.put('/products/removes', { ids })
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
    product.value = initModel();
  }
  return {
    product,
    products,
    reLoaded,
    isLoading,
    getList,
    getListByLevel,
    getById,
    resetForm,
    create,
    update,
    remove,
    removes
  }
})
