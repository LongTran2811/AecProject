<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { ArrowLeft, ArrowRight } from '@element-plus/icons-vue'

const props = defineProps({
  dataNews: {
    type: Array,
    required: true,
    default: () => [],
  },
  title: {
    type: String,
    default: 'Flash Sale',
  },
  subtitle: {
    type: String,
    default: 'Mua sắm thả ga',
  },
  itemsPerPage: {
    type: Number,
    default: 4,
  },
  formatDate: {
    type: Function,
    default: (date) => date,
  },
})

const carouselIndex = ref(0)

const hasNews = computed(() => props.dataNews.length > 0)

const visibleNews = computed(() => {
  if (!hasNews.value) return []
  return Array.from(
    { length: props.itemsPerPage },
    (_, i) => props.dataNews[(carouselIndex.value + i) % props.dataNews.length],
  )
})

let intervalId
onMounted(() => {
  if (hasNews.value) {
    intervalId = setInterval(() => {
      carouselIndex.value = (carouselIndex.value + 1) % props.dataNews.length
    }, 5000)
  }
})

onUnmounted(() => {
  clearInterval(intervalId)
})

const prevSlide = () => {
  if (hasNews.value) {
    carouselIndex.value = (carouselIndex.value - 1 + props.dataNews.length) % props.dataNews.length
  }
}

const nextSlide = () => {
  if (hasNews.value) {
    carouselIndex.value = (carouselIndex.value + 1) % props.dataNews.length
  }
}
</script>

<template>
  <div class="title-container">
    <div class="title">
      <h1>{{ title }}</h1>
      <span>{{ subtitle }}</span>
    </div>
    <div class="carousel-navigation">
      <button @click="prevSlide" class="nav-button">
        <el-icon :size="18"><ArrowLeft /></el-icon>
      </button>
      <button @click="nextSlide" class="nav-button">
        <el-icon :size="18"><ArrowRight /></el-icon>
      </button>
    </div>
  </div>
  <div class="news-carousel-container" v-if="hasNews">
    <div class="news-carousel">
      <transition-group
        name="carousel-fade"
        tag="div"
        class="news-wrapper grid grid-cols-5 gap-4"
        enter-active-class="transition-all duration-500 ease-in-out"
        leave-active-class="transition-all duration-500 ease-in-out"
        enter-from-class="opacity-0 translate-x-8"
        enter-to-class="opacity-100 translate-x-0"
        leave-from-class="opacity-100 translate-x-0"
        leave-to-class="opacity-0 -translate-x-8"
      >
        <el-card
          v-for="(item, idx) in visibleNews"
          :key="item.id || idx"
          class="w-full h-[260px] md:h-[330px] relative mx-auto rounded-lg border bg-white shadow hover:shadow-lg transition-shadow duration-500"
        >
          <div class="w-full h-[100px] md:h-[200px] p-1 md:p-2 flex items-center justify-center">
            <img :src="item.img" :alt="item.nane" class="w-full h-full object-cover rounded" />
          </div>
          <div class="h-[56px] md:h-[70px] flex items-center justify-start">
            <span class="font-bold text-xs md:text-md text-left line-clamp-2">{{ item.nane }}</span>
          </div>
          <div class="absolute bottom-2 left-2 px-2">
            <p class="text-gray-400 text-xs md:text-sm line-through">
              {{ item.priceOriginal }} <span class="underline">đ</span>
            </p>
            <p class="text-[#FBA544] text-md md:text-md font-bold">
              {{ item.price }} <span class="underline">đ</span>
            </p>
          </div>
        </el-card>
      </transition-group>
    </div>
  </div>
  <p v-else class="no-news">Hiện tại không có sản phẩm.</p>
</template>

<style scoped>
.news-carousel-container {
  position: relative;
  margin: auto;
  overflow: hidden;
  padding: 20px 0;
}

.nav-button {
  background-color: rgba(85, 85, 85, 0.6);
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30px;
  width: 30px;
  transition: background 0.3s;
}

.nav-button:hover {
  background-color: rgba(0, 0, 0, 0.8);
}

.news-carousel {
  overflow: hidden;
  display: grid;
  /* justify-content: center; */
  width: 100%;
  margin: 0 auto;
}

.news-wrapper {
  justify-content: space-between;
}

.news-item {
  text-align: left;
  transition: transform 0.5s ease-in-out;
}

.news-link {
  text-decoration: none;
  display: block;
  color: inherit;
}

.news-image {
  width: 100%;
  height: 120px;
  border-radius: 5px;
  object-fit: cover;
}

.no-news {
  text-align: center;
  font-size: 16px;
  color: #888;
  margin-top: 20px;
}
.title-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 50px;
  padding: 0;
  margin-top: 10px;
}

.title-container .title {
  text-align: left;
}
.title-container .title h1 {
  margin: 0;
  font-size: 25px;
}
.title-container .title span {
  margin: 0;
  font-size: 14px;
}

.title-container .carousel-navigation {
  position: absolute;
  display: flex;
  right: 0;
  justify-content: right;
  gap: 10px;
}
@media (max-width: 990px) {
  .news-carousel-container {
    width: 100%;
  }
  .news-wrapper {
    display: grid;
    grid-template-columns: repeat(2, 1fr); /* 2 cột */
    grid-template-rows: repeat(2, 1fr); /* 2 hàng */
    gap: 10px;
    margin: 0 auto;
    width: 100%;
    padding-right: 15px;
  }

  .news-item {
    width: 100%;
  }

  .news-carousel {
    overflow: hidden;
    display: flex;
    justify-content: center;
    width: 100%;
    margin: 0 auto;
  }

  .news-image {
    width: 100%;
    height: 100px;
    border-radius: 5px;
    object-fit: cover;
  }
  .nav-button {
    height: 25px;
    width: 25px;
  }
}
</style>
