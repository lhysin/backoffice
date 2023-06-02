import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { manualChunksPlugin } from 'vite-plugin-webpackchunkname'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    manualChunksPlugin(),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  // axios의 baseUrl이 없으면 프록시 서버로 요청.
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8880',
      },
    },
  },
})
