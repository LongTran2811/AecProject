<script lang="ts" setup>
const tableData = [
  {
    id: 1,
    nane: 'Laptop MSI Modern 14 C12MO-660VN',
    price: '11.590.000',
    quantity: '1',
  },
  {
    id: 2,
    nane: 'Laptop HP 15-FC0086AU A6VV9PA',
    price: '12.290.000',
    quantity: '3',
  },
  {
    id: 3,
    nane: 'Laptop Lenovo IdeaPad Slim 5 14Q8X9 83HL000KVN',
    price: '19.490.000',
    quantity: '2',
  },
]
</script>

<template>
  <div class="space-y-4">
    <div>
      <el-card>
        <template #header>
          <i class="fa-solid fa-location-dot"></i>
          <span class="ml-2 font-semibold">Địa chỉ nhận hàng</span>
        </template>
        <div class="flex justify-between items-center">
          <div class="space-y-2">
            <h3 class="font-bold">Nguyễn Văn A</h3>
            <p><span class="font-semibold">Địa chỉ: </span> Số 5 đường Trần Duy Hưng, TP Hà Nội</p>
          </div>
          <button
            class="uppercase !text-xs !font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white py-2 px-4 rounded-sm"
          >
            Thay đổi
          </button>
        </div>
      </el-card>
    </div>
    <div>
      <el-card>
        <template #header>
          <i class="fa-solid fa-bag-shopping"></i>
          <span class="ml-2 font-semibold">Danh sách sản phẩm</span>
        </template>
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="id" label="ID" width="180" />
          <el-table-column prop="nane" label="Tên sản phẩm" />
          <el-table-column prop="price" label="Giá" />
          <el-table-column prop="quantity" label="Số lượng" />
          <el-table-column label="Thành tiền"></el-table-column>
        </el-table>
        <p class="text-right font-bold text-[#FBA544] mt-5">
          <span class="font-bold text-black">Tổng cộng: </span> 000.000 <span class="text-sm underline">đ</span>
        </p>
      </el-card>
    </div>
    <div>
      <el-card class="space-y-2">
        <template #header>
          <i class="fa-solid fa-money-check-dollar"></i>
          <span class="ml-2 font-semibold">Phương thức thanh toán</span>
        </template>
        <div class="space-y-1">
          <p class="text-right font-bold text-sm">
            <span class=" text-black text-sm">Tổng tiền hàng: </span> 000.000 <span class="text-sm underline">đ</span>
          </p>
          <p class="text-right font-bold text-sm">
            <span class=" text-black text-sm">Phí vận chuyển: </span> 000.000 <span class="text-sm underline">đ</span>
          </p>
          <p class="text-right font-bold text-[#FBA544]">
            <span class=" text-black text-md">Tổng thanh toán: </span> 000.000 <span class="text-sm underline">đ</span>
          </p>
        </div>
        <div class="justify-end flex space-x-2 mt-4">
          <button
            class="uppercase !text-xs !font-bold bg-[#333333] hover:bg-[#5a5a5a] duration-300 text-white py-2 px-4 rounded-sm"
          >
            Quay lại
          </button>
          <button
            class="uppercase !text-xs !font-bold bg-[#FBA544] hover:bg-[#ffba6b] duration-300 text-white py-2 px-4 rounded-sm"
          >
            Đặt hàng
          </button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped></style>
