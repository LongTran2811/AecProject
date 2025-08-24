<script setup lang="ts">
import { computed, ref } from "vue";

defineOptions({ name: "FEInput" });

const props = defineProps({
  id: { type: String, required: true },
  autoFocus: { type: Boolean, default: false },
  placeHolder: { type: String, default: "" },
  className: { type: String, default: "w-full" },
  size: { type: String, default: "small" },
  rules: { type: Object, default: () => ({}) },
  submitted: { type: Boolean, default: null },
  icon: { type: String, default: null }, // ex: "fa-solid fa-user"
  iconPosition: { type: String, default: "left" }, // "left" hoặc "right"
  readonly: { type: Boolean, default: false },
});

const model = defineModel<string>();

const errorMessage = ref("");

// validate đơn giản
const validateInput = () => {
  errorMessage.value = "";
  if (props.rules?.required && !model.value) {
    errorMessage.value = props.rules.message || "Trường này là bắt buộc";
  }
};

const hasError = computed(() => !!errorMessage.value && props.submitted);
</script>

<template>
  <div class="flex flex-col gap-1">
    <div class="relative">
      <!-- Icon bên trái -->
      <i
        v-if="icon && iconPosition === 'left'"
        :class="[icon, 'absolute left-3 top-1/2 -translate-y-1/2 text-gray-400']"
      ></i>

      <!-- Input -->
      <input
        v-model="model"
        :id="id"
        :placeholder="placeHolder"
        :readonly="readonly"
        :autofocus="autoFocus"
        @blur="validateInput"
        :class="[
          'border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 px-3 py-2 w-full',
          icon && iconPosition === 'left' ? 'pl-10' : '',
          icon && iconPosition === 'right' ? 'pr-10' : '',
          className
        ]"
      />

      <!-- Icon bên phải -->
      <i
        v-if="icon && iconPosition === 'right'"
        :class="[icon, 'absolute right-3 top-1/2 -translate-y-1/2 text-gray-400']"
      ></i>
    </div>

    <!-- Hiển thị lỗi -->
    <span v-if="hasError" class="text-red-500 text-xs">
      {{ errorMessage }}
    </span>
  </div>
</template>
