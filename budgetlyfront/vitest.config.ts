import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  test: {
    environment: 'jsdom', // OVO MORA BITI TU!
    globals: true,
    setupFiles: './src/setupTests.ts',
  },
});