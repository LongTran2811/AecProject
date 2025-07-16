<script lang="ts" setup>
import * as echarts from 'echarts'
import { ref, onMounted } from 'vue'
import { useTransition } from '@vueuse/core'
import { ChatLineRound, Male } from '@element-plus/icons-vue'

const source = ref(0)
const outputValue = useTransition(source, {
  duration: 1500,
})
source.value = 172000

// Biểu đ
const barChartRef = ref(null)
const pieChartRef = ref(null)

onMounted(() => {
  // Biểu đồ cột
  const barChart = echarts.init(barChartRef.value)
  barChart.setOption({
    title: {
      text: 'Biểu đồ cột',
      textStyle: {
        fontFamily: '"Helvetica Neue", Helvetica, Arial, sans-serif',
        fontSize: 16,
        fontWeight: 'bold',
        color: '#303133', // giống màu text mặc định của Element Plus
      },
    },
    tooltip: {},
    xAxis: { data: ['A', 'B', 'C', 'D'] },
    yAxis: {},
    series: [
      {
        name: 'Số lượng',
        type: 'bar',
        data: [5, 20, 36, 10],
        itemStyle: {
          color: '#409EFF', // Màu Element Plus
        },
      },
    ],
  })

  // Biểu đồ tròn
  const pieChart = echarts.init(pieChartRef.value)
  pieChart.setOption({
    title: {
      text: 'Biểu đồ tròn',
      left: 'center',
      textStyle: {
        fontFamily: '"Helvetica Neue", Helvetica, Arial, sans-serif',
        fontSize: 16,
        fontWeight: 'bold',
        color: '#303133', // giống màu text mặc định của Element Plus
      },
    },
    tooltip: { trigger: 'item' },
    legend: {
      orient: 'horizontal',
      bottom: 'bottom',
    },
    series: [
      {
        // name: 'Phân bổ',
        type: 'pie',
        radius: '70%',
        data: [
          { value: 40, name: 'A' },
          { value: 20, name: 'B' },
          { value: 30, name: 'C' },
          { value: 10, name: 'D' },
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  })
})
</script>

<style scoped>
.el-col {
  text-align: center;
}
</style>
<template>
  <div class="w-full h-full">
    <el-div class="w-full h-[650px] p-4 space-y-4">
      <el-card class="w-full items-center justify-center">
        <el-row>
          <el-col :span="6">
            <el-statistic title="Các đơn hàng đang xử lý" :value="outputValue" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="Các đơn hàng đã xử lý" :value="outputValue" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="Tất cả đơn hàng" :value="outputValue" />
          </el-col>
          <el-col :span="6">
            <el-statistic title="Tồn kho" :value="outputValue" />
          </el-col>
        </el-row>
      </el-card>
      <div class="flex w-full items-center justify-center gap-4">
        <el-card class="w-2/3">
          <div ref="barChartRef" style="width: 100%; height: 400px"></div>
        </el-card>

        <el-card class="w-1/3">
          <div ref="pieChartRef" style="width: 100%; height: 400px"></div>
        </el-card>
      </div>
    </el-div>
  </div>
</template>
