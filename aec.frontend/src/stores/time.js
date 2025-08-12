import { defineStore } from 'pinia'
import { ref } from 'vue'
import dayjs from 'dayjs'

export const useTimeStore = defineStore('time', () => {
  const currentDate = ref(dayjs().format('DD/MM/YYYY'))
  const currentTime = ref(dayjs().format('HH:mm:ss'))

  // Chạy timer nền
  setInterval(() => {
    const now = dayjs()
    currentDate.value = now.format('DD/MM/YYYY')
    currentTime.value = now.format('HH:mm:ss')
  }, 1000)

  return { currentDate, currentTime }
})
