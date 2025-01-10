import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
    plugins: [react()],
    server: {
      proxy: {
        "/db": {
          target: "http://localhost:8080", // Spring Boot backend
          changeOrigin: true,
        },
      },
    },
  });