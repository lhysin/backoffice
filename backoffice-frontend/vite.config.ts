import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    proxy: {
      // 프록시 설정을 추가합니다.
      '/api': {
        target: 'http://localhost:8880', // 실제 API 서버의 주소로 변경해야 합니다.
      },
    },
  },
})
