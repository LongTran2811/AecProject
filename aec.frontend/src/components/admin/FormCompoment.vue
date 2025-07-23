<template>
  <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    status-icon
    :rules="rules"
    label-width="auto"
    class="demo-ruleForm w-full px-6 !space-y-6"
  >
  <div class="my-4 flex justify-between items-center">
    <h3>Quản lý danh mục sản phẩm</h3>
    <el-button type="primary" @click="router.back()" plain>Quay lại</el-button>
  </div>
    <el-form-item label="Password" prop="pass">
      <el-input v-model="ruleForm.pass" type="password" autocomplete="off" />
    </el-form-item>
    <el-form-item label="Confirm" prop="checkPass">
      <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off" />
    </el-form-item>
    <el-form-item label="Age" prop="age">
      <el-input v-model.number="ruleForm.age" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)"> Submit </el-button>
      <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElNotification } from 'element-plus'

import { useRouter } from 'vue-router'

const router = useRouter()
const ruleFormRef = ref<FormInstance>()

const checkAge = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('Please input the age'))
  }
  setTimeout(() => {
    if (!Number.isInteger(value)) {
      callback(new Error('Please input digits'))
    } else {
      if (value < 18) {
        callback(new Error('Age must be greater than 18'))
      } else {
        callback()
      }
    }
  }, 1000)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.pass) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  pass: '',
  checkPass: '',
  age: '',
})

const rules = reactive<FormRules<typeof ruleForm>>({
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  age: [{ validator: checkAge, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      ElNotification({
        title: 'Warning',
        message: 'Delete completed',
        type: 'success',
        position: 'top-right', // 👈 Vị trí góc trên phải
        duration: 3000,
        customClass: 'custom-warning-notification',
      })
      router.push('/manager')
    } else {
      console.log('error submit!')
      ElNotification({
        title: 'Error',
        message: 'Delete completed',
        type: 'success',
        position: 'top-right', // 👈 Vị trí góc trên phải
        duration: 3000,
        customClass: 'custom-error-notification',
      })
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
