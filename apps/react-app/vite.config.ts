import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    watch: {
      usePolling: true,
    },
    proxy: {
      // 所有發到 /api 開頭的請求，都會轉發到 mock server
      '/se': {
        target: 'http://localhost:3000',
        changeOrigin: true,
        // 避免 /api/login 被轉成 /api/api/login
        rewrite: path => path.replace(/^\/api/, '')
      }
    }
  },
})
