import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import { Layout, Menu, Typography } from 'antd';
import TransactionsPage from './components/Transactions/TransactionsPage';
import SavingsPage from './components/Savings/SavingsPage';
import { DollarOutlined, FundOutlined, HomeOutlined } from '@ant-design/icons';
import HomePage from './components/HomePage';

const { Sider, Content } = Layout;
const { Title } = Typography;

const App: React.FC = () => {
  const navigate = useNavigate();
  const [selectedKey, setSelectedKey] = useState<string>('home');

  useEffect(() => {
    if (window.location.pathname !== '/pocetna') {
      navigate('/pocetna', { replace: true });
    }
    // eslint-disable-next-line
  }, []);

  const handleMenuClick = (e: any) => {
    setSelectedKey(e.key);
    if (e.key === 'home') {
      navigate('/pocetna');
    } else if (e.key === 'transactions') {
      navigate('/Transakcije');
    } else if (e.key === 'savings') {
      navigate('/Savings');
    }
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider>
        <div style={{ padding: 16, textAlign: 'center' }}>
          <Title level={3} style={{ color: 'white', margin: 0 }}>Budgetly</Title>
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={[selectedKey]}
          onClick={handleMenuClick}
          items={[
            { key: 'home',         icon: <HomeOutlined />,      label: 'Početna'      },
            { key: 'transactions', icon: <DollarOutlined />,    label: 'Transakcije'  },
            { key: 'savings',      icon: <FundOutlined />,      label: 'Štednje'      },
          ]}
        />
      </Sider>

      <Layout>
        <Content style={{ margin: 16 }}>
          <Routes>
            <Route path="/" element={<Navigate to="/pocetna" replace />} />
            <Route path="/pocetna" element={<HomePage />} />
            <Route path="/Transakcije/*" element={<TransactionsPage />} />
            <Route path="/Savings"      element={<SavingsPage />} />
            <Route path="/Savings/:id"  element={<SavingsPage />} />
            <Route path="*" element={<Navigate to="/pocetna" replace />} />
          </Routes>
        </Content>
      </Layout>
    </Layout>
  );
};

const AppWrapper: React.FC = () => (
  <Router>
    <App />
  </Router>
);

export default AppWrapper;