<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import type { PropType } from 'vue';

defineOptions({ name: 'FEMulSelect' });

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

  loadOptions: {
    type: Function as PropType<() => Promise<Option[]>>,
    required: false
  }
});

// 👇 multiple => array
const model = defineModel<(string | number)[]>();

const options = ref<Option[]>([]);
const errorMessage = ref('');
const loading = ref(false);

const validateInput = () => {
  errorMessage.value = '';
  if (props.rules?.required && (!model.value || model.value.length === 0)) {
    errorMessage.value = props.rules.message || 'Trường này là bắt buộc';
  }
};
const hasError = computed(() => !!errorMessage.value && props.submitted);

onMounted(async () => {
  if (props.loadOptions) {
    loading.value = true;
    try {
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
      multiple
      filterable
      clearable
      :placeholder="placeHolder"
      :class="className"
      :id="id"
      :loading="loading"
      collapse-tags
      collapse-tags-tooltip
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
