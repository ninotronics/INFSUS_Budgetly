import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
//import 'antd/dist/antd.min.css';
import 'antd/dist/reset.css';
const container = document.getElementById('root');
if (!container) {
  throw new Error('Could not find root element');
}

const root = createRoot(container);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
