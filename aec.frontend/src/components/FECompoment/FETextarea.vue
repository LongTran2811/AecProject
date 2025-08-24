<script lang="ts" setup>
import { computed, ref } from 'vue';

defineOptions({
  name: 'FETextarea'
});

const props = defineProps({
  id: {
    type: String,
    required: true
  },
  autoFocus: Boolean,
  placeHolder: {
    type: String,
    default: ''
  },
  className: {
    type: String,
    default: 'w-full'
  },
  rules: {
    type: Object,
    default: () => ({})
  },
  submitted: {
    type: Boolean,
    default: false
  },
  icon: {
    type: String,
    default: null
  },
  rows: {
    type: Number,
    default: 6
  },
  autoResize: {
    type: Boolean,
    default: false
  }
});

const model = defineModel({ type: String });
const errorMessage = ref("");

// validate đơn giản
const validateInput = () => {
  errorMessage.value = "";
  if (props.rules?.required && !model.value) {
    errorMessage.value = props.rules.message || "Trường này là bắt buộc";
  }
};

const hasError = computed(() => !!errorMessage.value && props.submitted);
const autosizeValue = computed(() => {
  return props.autoResize ? { minRows: props.rows } : false;
});
</script>

<template>
  <div>
    <el-input
    v-model="model"
    type="textarea"
    :rows="rows"
    :autosize="autosizeValue"
    :placeholder="placeHolder"
    :class="className"
    @blur="validateInput"
  />
    <!-- Hiển thị lỗi -->
    <span v-if="hasError" class="text-red-500 text-xs">
      {{ errorMessage }}
    </span>
  </div>

</template>
