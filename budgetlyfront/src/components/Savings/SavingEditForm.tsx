// src/components/Savings/SavingsEditForm.tsx
import React from 'react';
import { Form, Input, DatePicker, InputNumber, Button, message } from 'antd';
import moment from 'moment';
import { StednjaResponseDTO, StednjaRequestDTO } from '../../types1';
import { updateSaving } from '../../api';

interface Props {
  saving: StednjaResponseDTO;
  onSaved: () => void;
  onCancel: () => void;
}

const SavingsEditForm: React.FC<Props> = ({ saving, onSaved, onCancel }) => {
  const [form] = Form.useForm();

  // prefill form with existing values
  const initialValues = {
    naziv: saving.naziv,
    opis: saving.opis,
    datumKreiranja: moment(saving.datumKreiranja),
    datumKraj: moment(saving.datumKraj),
    ciljniIznos: saving.ciljniIznos,
    trenutniIznos: saving.trenutniIznos,
    korisnikId: saving.korisnikId,
  };

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

    console.log(payload);

    updateSaving(saving.id, payload)
      .then(() => {
        message.success('Štednja uspješno ažurirana');
        onSaved();
      })
      .catch(() => message.error('Greška pri ažuriranju štednje'));
  };

  return (
    <Form
      form={form}
      layout="vertical"
      initialValues={initialValues}
      onFinish={onFinish}
    >
      <Form.Item name="naziv" label="Naziv štednje" rules={[{ required: true }]}>
        <Input />
      </Form.Item>
      <Form.Item name="opis" label="Opis (opcional)">
        <Input.TextArea rows={2} />
      </Form.Item>
      <Form.Item
        name="datumKreiranja"
        label="Datum početka"
        rules={[{ required: true }]}
      >
        <DatePicker style={{ width: '100%' }} />
      </Form.Item>
      <Form.Item name="datumKraj" label="Datum kraja" rules={[{ required: true }]}>
        <DatePicker style={{ width: '100%' }} />
      </Form.Item>
      <Form.Item
        name="ciljniIznos"
        label="Ciljni iznos"
        rules={[{ required: true }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item>
      {/* <Form.Item
        name="trenutniIznos"
        label="Trenutni iznos"
        rules={[{ required: true }]}
      >
        <InputNumber min={0} style={{ width: '100%' }} />
      </Form.Item> */}
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Spremi izmjene
        </Button>
        <Button style={{ marginLeft: 8 }} onClick={onCancel}>
          Odustani
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SavingsEditForm;
