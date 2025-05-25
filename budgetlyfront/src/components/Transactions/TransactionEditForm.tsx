import React, { useEffect } from 'react';
import { Form, Input, InputNumber, Button, message, Select } from 'antd';
import { TransactionResponseDTO, PrihodRequestDTO, TrosakRequestDTO, PrihodEnum, TrosakEnum } from '../../types1';
import { updateIncome, updateExpense } from '../../api';

interface Props {
  tx: TransactionResponseDTO;
  currency: 'EUR' | 'USD';
  exchangeRate: number;
  onSaved: () => void;
  onCancel: () => void;
}

const TransactionEditForm: React.FC<Props> = ({ tx, currency, exchangeRate, onSaved, onCancel }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    const amount = currency === 'USD'
      ? Number((tx.iznos * exchangeRate).toFixed(2))
      : tx.iznos;
    form.setFieldsValue({ iznos: amount });
  }, [currency, exchangeRate, form, tx.iznos]);

  const initialValues = {
    id: tx.id,
    vrsta: tx.vrsta,
    iznos: currency === 'USD'
      ? Number((tx.iznos * exchangeRate).toFixed(2))
      : tx.iznos,
    opis: tx.opis,
    korisnikId: tx.korisnikId,
    kategorija: tx.vrsta === 'PRIHOD'
      ? (tx as any).prihodKategorija
      : (tx as any).trosakKategorija,
  };

  const onFinish = (values: any) => {
    let amountEUR = values.iznos;
    if (currency === 'USD') {
      amountEUR = Number((amountEUR / exchangeRate).toFixed(2));
    }

    if (tx.vrsta === 'PRIHOD') {
      const payload: PrihodRequestDTO = {
        opis: values.opis,
        iznos: amountEUR,
        vrsta: tx.vrsta,
        korisnikId: values.korisnikId,
        prihodKategorija: values.kategorija,
      };
      updateIncome(tx.id, payload)
        .then(() => {
          message.success('Prihod uspješno ažuriran');
          onSaved();
          onCancel();
        })
        .catch(() => message.error('Greška pri ažuriranju prihoda'));
    } else {
      const payload: TrosakRequestDTO = {
        opis: values.opis,
        iznos: amountEUR,
        vrsta: tx.vrsta,
        korisnikId: values.korisnikId,
        trosakKategorija: values.kategorija,
      };
      updateExpense(tx.id, payload)
        .then(() => {
          message.success('Trošak uspješno ažuriran');
          onSaved();
          onCancel();
        })
        .catch(() => message.error('Greška pri ažuriranju troška'));
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      initialValues={initialValues}
      onFinish={onFinish}
    >
      <Form.Item
        name="iznos"
        label={`Iznos (${currency})`}
        rules={[{ required: true, message: 'Unesite iznos' }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item>

      <Form.Item name="opis" label="Opis">
        <Input.TextArea rows={2} />
      </Form.Item>

      <Form.Item
        name="kategorija"
        label="Kategorija"
        rules={[{ required: true, message: 'Unesite kategoriju' }]}
      >
        <Select<string>
          placeholder="Odaberite kategoriju"
          options={
            tx.vrsta === 'PRIHOD'
              ? Object.values(PrihodEnum).map(val => ({ value: val as string, label: val as string }))
              : Object.values(TrosakEnum).map(val => ({ value: val as string, label: val as string }))
          }
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Spremi
        </Button>
        <Button style={{ marginLeft: 8 }} onClick={onCancel}>
          Odustani
        </Button>
      </Form.Item>
    </Form>
  );
};

export default TransactionEditForm;