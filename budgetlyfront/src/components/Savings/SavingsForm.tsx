// src/components/Savings/SavingsForm.tsx
import React from 'react';
import { Form, Input, DatePicker, InputNumber, Button, message } from 'antd';
import moment from 'moment';
import { createSaving } from '../../api';
import { StednjaRequestDTO } from '../../types1';

interface Props {
  onSaved: () => void;
}

const SavingsForm: React.FC<Props> = ({ onSaved }) => {
  const [form] = Form.useForm();

  const onFinish = (values: any) => {
    const payload: StednjaRequestDTO = {
      naziv: values.naziv,
      opis: values.opis,
      datumKreiranja: (values.datumKreiranja as moment.Moment).format('YYYY-MM-DD'),
      datumKraj: (values.datumKraj as moment.Moment).format('YYYY-MM-DD'),
      ciljniIznos: values.ciljniIznos,
      trenutniIznos: values.trenutniIznos,
      korisnikId: 1,
    };

    createSaving(payload)
      .then(() => {
        message.success('Štednja uspješno kreirana');
        form.resetFields();
        onSaved();
      })
      .catch(() => {
        message.error('Greška pri kreiranju štednje');
      });
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={onFinish}
      initialValues={{
        trenutniIznos: 0,
      }}
    >
      <Form.Item
        name="naziv"
        label="Naziv štednje"
        rules={[{ required: true, message: 'Unesite naziv štednje' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item name="opis" label="Opis (opcional)">
        <Input.TextArea rows={2} />
      </Form.Item>

      <Form.Item
        name="datumKreiranja"
        label="Datum kreiranja"
        rules={[{ required: true, message: 'Odaberite datum kreiranja' }]}
      >
        <DatePicker style={{ width: '100%' }} />
      </Form.Item>

      <Form.Item
        name="datumKraj"
        label="Datum kraja"
        rules={[{ required: true, message: 'Odaberite datum kraja' }]}
      >
        <DatePicker style={{ width: '100%' }} />
      </Form.Item>

      <Form.Item
        name="ciljniIznos"
        label="Ciljni iznos"
        rules={[{ required: true, message: 'Unesite ciljni iznos' }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item>

      <Form.Item
        name="trenutniIznos"
        label="Trenutni iznos"
        rules={[{ required: true, message: 'Unesite trenutni iznos' }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Spremi štednju
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SavingsForm;
