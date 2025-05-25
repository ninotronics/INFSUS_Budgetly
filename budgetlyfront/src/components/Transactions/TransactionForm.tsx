import React, { useState } from 'react';
import { Form, Input, InputNumber, Radio, Button, message, Select } from 'antd';
import { createIncome, createExpense } from '../../api';
import { PrihodRequestDTO, TrosakRequestDTO, PrihodEnum, TrosakEnum } from '../../types1';

interface Props {
  onSaved: () => void;
  currency: 'EUR' | 'USD';
  exchangeRate: number;
}

const TransactionForm: React.FC<Props> = ({ onSaved, currency, exchangeRate }) => {
  const [form] = Form.useForm();
  const [vrsta, setVrsta] = useState<'PRIHOD' | 'TROSAK'>('TROSAK');

  const onFinish = async (values: any) => {
    const postojiDrugaVrsta = await checkIfOtherTypeExists(values.opis, values.datumTransakcije, values.vrsta);
    if (postojiDrugaVrsta) {
      message.error('Ne može postojati i prihod i trošak s istim opisom na isti dan!');
      return;
    }

    let amountEUR = values.iznos;
    if (currency === 'USD') {
      amountEUR = Number((amountEUR / exchangeRate).toFixed(2));
    }
    if (values.vrsta === 'PRIHOD') {
      const payload: PrihodRequestDTO = {
        opis: values.opis,
        iznos: amountEUR,
        vrsta: values.vrsta,
        korisnikId: 1,
        prihodKategorija: values.kategorija,
      };
      createIncome(payload)
        .then(() => {
          message.success('Prihod uspješno dodan');
          form.resetFields();
          onSaved();
        })
        .catch(() => message.error('Greška pri dodavanju prihoda'));
    } else {
      const payload: TrosakRequestDTO = {
        opis: values.opis,
        iznos: amountEUR,
        vrsta: values.vrsta,
        korisnikId: 1,
        trosakKategorija: values.kategorija,
      };
      createExpense(payload)
        .then(() => {
          message.success('Trošak uspješno dodan');
          form.resetFields();
          onSaved();
        })
        .catch(() => message.error('Greška pri dodavanju troška'));
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={onFinish}
      initialValues={{ vrsta: 'TROSAK' }}
    >
      <Form.Item
        name="vrsta"
        label="Vrsta transakcije"
        rules={[{ required: true, message: 'Odaberite vrstu' }]}
      >
        <Radio.Group
          onChange={e => setVrsta(e.target.value)}
        >
          <Radio value="PRIHOD">Prihod</Radio>
          <Radio value="TROSAK">Trošak</Radio>
        </Radio.Group>
      </Form.Item>
      <Form.Item
        name="iznos"
        label="Iznos"
        rules={[{ required: true, message: 'Unesite iznos' }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item>
      <Form.Item name="opis" label="Opis">
        <Input.TextArea />
      </Form.Item>
      <Form.Item
        name="kategorija"
        label="Kategorija"
        rules={[{ required: true, message: 'Unesite kategoriju' }]}
      >
        <Select<{ value: PrihodEnum | TrosakEnum; label: string }>
          placeholder="Odaberite kategoriju"
          options={
            vrsta === 'PRIHOD'
              ? Object.values(PrihodEnum).map(val => ({ value: val, label: val }))
              : Object.values(TrosakEnum).map(val => ({ value: val, label: val }))
          }
        />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Dodaj transakciju
        </Button>
      </Form.Item>
    </Form>
  );
};

export default TransactionForm;

async function checkIfOtherTypeExists(
  opis: any,
  datumTransakcije: any,
  vrsta: 'PRIHOD' | 'TROSAK'
): Promise<boolean> {
  return false;
}