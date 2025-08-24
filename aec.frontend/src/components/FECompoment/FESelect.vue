<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import type { PropType } from 'vue';

defineOptions({ name: 'FESelect' });

interface Option {
  label: string;
  value: string | number;
}

const props = defineProps({
  id: { type: String, required: true },
  placeHolder: { type: String, default: '' },
  className: { type: String, default: 'w-full' },
  rules: { type: Object, default: () => ({}) },
  submitted: { type: Boolean, default: false },

  // 👇 Quan trọng: dùng PropType để khai báo đúng kiểu là "hàm trả Promise<Option[]>"
  loadOptions: {
    type: Function as PropType<() => Promise<Option[]>>,
    required: false
  }
});

const model = defineModel<string | number | null>();
const options = ref<Option[]>([]);
const errorMessage = ref('');
const loading = ref(false);

const validateInput = () => {
  errorMessage.value = '';
  if (props.rules?.required && !model.value) {
    errorMessage.value = props.rules.message || 'Trường này là bắt buộc';
  }
};
const hasError = computed(() => !!errorMessage.value && props.submitted);

onMounted(async () => {
  if (props.loadOptions) {
    loading.value = true;
    try {
      // 👇 Bây giờ TS hiểu loadOptions là một hàm -> gọi được
      options.value = await props.loadOptions();
    } finally {
      loading.value = false;
    }
  }
});
</script>

<template>
  <div>
    <el-select
      v-model="model"
      :placeholder="placeHolder"
      :class="className"
      :id="id"
      filterable
      clearable
      :loading="loading"
      @blur="validateInput"
    >
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>

    <span v-if="hasError" class="text-red-500 text-xs">
      {{ errorMessage }}
    </span>
  </div>
</template>
