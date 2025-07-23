import { createI18n } from 'vue-i18n'

const messages = {
  vi: {
    form: {
      required: 'Trường này không thể để trống',
    },
  },
}

const i18n = createI18n({
  legacy: false, // nếu dùng Composition API
  locale: 'vi',
  messages,
})

export { i18n }  // ← PHẢI export đúng tên
