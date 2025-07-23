import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import api from '@/axios/api';

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
      .then(response => categories.value = response.data)
      .catch(err => console.error("Error: ", err));
  }

  return {
    categories,
    category,
    reLoaded,
    isLoading,
    getList
  }
})
