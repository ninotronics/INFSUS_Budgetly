// src/components/Transactions/TransactionsAllPage.tsx
import React, { useEffect, useState } from 'react';
import {
  Tabs,
  Form,
  Input,
  InputNumber,
  DatePicker,
  Radio,
  Button,
  Modal,
  message,
  Space,
  Dropdown,
  Menu,
  Table,
  Alert,
  Spin,
  Divider,
  Tag,
  Popconfirm,
} from 'antd';
import moment from 'moment';
import {
  fetchIncomes,
  fetchExpenses,
  deleteIncome,
  deleteExpense,
  fetchValutas,
} from '../../api';
import {
  PrihodResponseDTO,
  TrosakResponseDTO,
  ValutaResponseDTO,
} from '../../types1';
import TransactionForm from './TransactionForm';
import TransactionEditForm from './TransactionEditForm';


type UnifiedTx =
  | (PrihodResponseDTO & { vrsta: 'PRIHOD' })
  | (TrosakResponseDTO & { vrsta: 'TROSAK' });

const TransactionsAllPage: React.FC = () => {
  const exchangeRates: Record<'EUR'|'USD', number> = { EUR: 1, USD: 1.1 };

  const [txList, setTxList] = useState<UnifiedTx[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [createVisible, setCreateVisible] = useState(false);
  const [editing, setEditing] = useState<UnifiedTx | null>(null);
  const [editVisible, setEditVisible] = useState(false);

  const [searchVisible, setSearchVisible] = useState(false);
  const [form] = Form.useForm();

  const [currency, setCurrency] = useState<'EUR' | 'USD'>('EUR');
  const [currencies, setCurrencies] = useState<ValutaResponseDTO[]>([]);
  const [selectedCurrency, setSelectedCurrency] = useState<ValutaResponseDTO | null>(null);

  const currencyMenu = (
    <Menu onClick={({ key }) => setCurrency(key as 'EUR' | 'USD')}>
      <Menu.Item key="EUR">EUR</Menu.Item>
      <Menu.Item key="USD">US Dollar</Menu.Item>
    </Menu>
  );

  useEffect(() => {
    fetchValutas()
      .then(cs => setCurrencies(cs))
      .catch(() => message.error('Greška pri dohvaćanju valuta'));
  }, []);

  useEffect(() => {
    const sel = currencies.find(c => c.kod === currency);
    setSelectedCurrency(sel || null);
  }, [currency, currencies]);

  const loadAll = () => {
    setLoading(true);
    setError(null);
    Promise.all([fetchIncomes(), fetchExpenses()])
      .then(([incomes, expenses]) => {
        const unified: UnifiedTx[] = [
          ...incomes.map(i => ({ ...i, vrsta: 'PRIHOD' as const })),
          ...expenses.map(e => ({ ...e, vrsta: 'TROSAK' as const })),
        ];
        unified.sort((a, b) =>
          moment(b.datumTransakcije).diff(moment(a.datumTransakcije))
        );
        setTxList(unified);
      })
      .catch(() => setError('Greška pri dohvaćanju transakcija'))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadAll();
  }, []);

  const applySearch = (values: any) => {
    const { vrsta, opis, iznos, korisnikId, kategorija } = values;
    setLoading(true);
    setError(null);

    const handleResult = (data: any[], getCategory: (x: any) => string) => {
      let filtered = data;
      if (opis) filtered = filtered.filter(x => x.opis?.toLowerCase().includes(opis.toLowerCase()));
      if (iznos != null) filtered = filtered.filter(x => x.iznos === iznos);
      if (korisnikId != null) filtered = filtered.filter(x => x.korisnikId === korisnikId);
      if (kategorija) filtered = filtered.filter(x => getCategory(x) === kategorija);
      const unified: UnifiedTx[] = filtered.map(x =>
        'prihodKategorija' in x
          ? { ...x, vrsta: 'PRIHOD' }
          : { ...x, vrsta: 'TROSAK' }
      );
      unified.sort((a, b) =>
        moment(b.datumTransakcije).diff(moment(a.datumTransakcije))
      );
      setTxList(unified);
    };

    if (vrsta === 'PRIHOD') {
      fetchIncomes()
        .then(incomes => handleResult(incomes, (x: PrihodResponseDTO) => x.prihodKategorija))
        .catch(() => setError('Greška pri pretraživanju prihoda'))
        .finally(() => { setLoading(false); setSearchVisible(false); });
    } else {
      fetchExpenses()
        .then(expenses => handleResult(expenses, (x: TrosakResponseDTO) => x.trosakKategorija))
        .catch(() => setError('Greška pri pretraživanju troškova'))
        .finally(() => { setLoading(false); setSearchVisible(false); });
    }
  };

  if (loading) return <Spin tip="Učitavanje..." />;
  if (error) return <Alert message={error} type="error" showIcon />;

  return (
    <>
      <Space style={{ marginBottom: 16 }}>
        <Button type="primary" onClick={() => setCreateVisible(true)}>
          Dodaj transakciju
        </Button>
        <Button onClick={() => setSearchVisible(true)}>
          Pretraži transakcije
        </Button>
        <Button onClick={loadAll}>Resetiraj</Button>
        <Dropdown.Button overlay={currencyMenu}>
          {selectedCurrency?.simbol || currency}
        </Dropdown.Button>
      </Space>
      <Divider />

      <Table<UnifiedTx>
        dataSource={txList}
        rowKey="id"
        pagination={{ pageSize: 20 }}
      >
        <Table.Column<UnifiedTx>
          title="Iznos"
          dataIndex="iznos"
          key="iznos"
          render={(amount: number) => {
            const rate = exchangeRates[currency];
            const converted = amount * rate;
            const symbol = selectedCurrency?.simbol || currency;
            return `${converted.toFixed(2)} ${symbol}`;
          }}
        />
        <Table.Column<UnifiedTx>
          title="Vrsta"
          dataIndex="vrsta"
          key="vrsta"
          render={v =>
            v === 'PRIHOD' ? <Tag color="green">Prihod</Tag> : <Tag color="red">Trošak</Tag>
          }
        />
        <Table.Column<UnifiedTx>
          title="Kategorija"
          key="kategorija"
          render={(_, record) =>
            record.vrsta === 'PRIHOD'
              ? (record as PrihodResponseDTO).prihodKategorija
              : (record as TrosakResponseDTO).trosakKategorija
          }
        />
        <Table.Column<UnifiedTx> title="Opis" dataIndex="opis" key="opis" />
        <Table.Column<UnifiedTx>
          title="Akcije"
          key="actions"
          render={(_, record) => (
            <>
              <Button
                type="link"
                onClick={() => { setEditing(record); setEditVisible(true); }}
              >
                Uredi
              </Button>
              <Popconfirm
                title="Jeste li sigurni da želite obrisati ovu transakciju?"
                onConfirm={() => {
                  const fn = record.vrsta === 'PRIHOD' ? deleteIncome : deleteExpense;
                  fn(record.id).then(() => { message.success('Transakcija obrisana'); loadAll(); });
                }}
                okText="Da"
                cancelText="Ne"
              >
                <Button type="link" danger>
                  Izbriši
                </Button>
              </Popconfirm>
            </>
          )}
        />
      </Table>

      {/* Create Transaction Modal */}
      <Modal
        title="Nova transakcija"
        visible={createVisible}
        footer={null}
        onCancel={() => setCreateVisible(false)}
        width={600}
      >
        <TransactionForm
          onSaved={() => { loadAll(); setCreateVisible(false); } } currency={'EUR'} exchangeRate={0}        />
      </Modal>
      <Modal
        title="Nova transakcija"
       visible={createVisible}
        footer={null}
        onCancel={() => setCreateVisible(false)}
        width={600}
      >
        <TransactionForm
          currency={currency}
          exchangeRate={exchangeRates[currency]}
          onSaved={() => { loadAll(); setCreateVisible(false); }}
        />
      </Modal>

      {/* Search Modal */}
      <Modal
        title="Pretraži transakcije"
        visible={searchVisible}
        footer={null}
        onCancel={() => setSearchVisible(false)}
        width={600}
      >
        <Form form={form} layout="vertical" onFinish={applySearch}>
          <Form.Item name="vrsta" label="Vrsta" initialValue="TROSAK">
            <Radio.Group>
              <Radio value="PRIHOD">Prihod</Radio>
              <Radio value="TROSAK">Trošak</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item name="opis" label="Opis sadrži">
            <Input />
          </Form.Item>
          <Form.Item name="iznos" label="Iznos">
            <InputNumber style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="korisnikId" label="ID korisnika">
            <InputNumber style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="kategorija" label="Kategorija">
            <Input />
          </Form.Item>
          <Form.Item>
            <Space>
              <Button type="primary" htmlType="submit">Pretraži</Button>
              <Button htmlType="button" onClick={() => form.resetFields()}>
                Resetiraj
              </Button>
              <Dropdown.Button overlay={currencyMenu}>
                {selectedCurrency?.simbol || currency}
              </Dropdown.Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>

      {/* Edit Transaction Modal */}
      <Modal
        title="Uredi transakciju"
        visible={editVisible}
        footer={null}
        onCancel={() => setEditVisible(false)}
        width={600}
      >
        {editing && (
         <TransactionEditForm
           tx={editing}
           currency={currency}
           exchangeRate={exchangeRates[currency]}
           onSaved={() => { loadAll(); setEditVisible(false); }}
           onCancel={() => setEditVisible(false)}
         />
      )}
      </Modal>
    </>
  );
};

export default TransactionsAllPage;
