// src/components/Savings/ReminderForm.tsx
import React from 'react';
import { Form, Input, DatePicker, Button, message } from 'antd';
import moment from 'moment';
import { createReminder } from '../../api';
import { PodsjetnikRequestDTO } from '../../types1';
import { useParams } from 'react-router-dom';

interface Props {
  savingId?: number;     
  onAdded: () => void;
  onCancel: () => void;
}

const ReminderForm: React.FC<Props> = ({ onAdded, onCancel }) => {
  const { id } = useParams<{ id: string }>();
  const savingId = Number(id);
  const [form] = Form.useForm();

  const onFinish = async (values: any) => {
    if (!savingId) {
      message.error('Ne mogu dohvatiti ID štednje');
      console.log("tu");
      return;
    }
    const payload: PodsjetnikRequestDTO = {
      stednjaId: savingId,
      naziv: values.naziv,
      opis: values.opis,
      datumPodsjetnika: (values.datumPodsjetnika as moment.Moment).format('YYYY-MM-DD HH:mm'),
    };
    console.log(payload);
    try {
      await createReminder(payload);
      message.success('Podsjetnik uspješno dodan');
      form.resetFields();
      onAdded();
    } catch {
      message.error('Greška pri dodavanju podsjetnika');
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={onFinish}
    >
      <Form.Item
        name="naziv"
        label="Naziv podsjetnika"
        rules={[{ required: true, message: 'Unesite naziv podsjetnika' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="opis"
        label="Opis (opcionalno)"
      >
        <Input.TextArea rows={2} />
      </Form.Item>

      <Form.Item
        name="datumPodsjetnika"
        label="Datum i vrijeme podsjetnika"
        rules={[{ required: true, message: 'Odaberite datum i vrijeme' }]}
      >
        <DatePicker
          showTime
          format="YYYY-MM-DD HH:mm"
          style={{ width: '100%' }}
        />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Dodaj podsjetnik
        </Button>
        <Button style={{ marginLeft: 8 }} onClick={onCancel}>
          Odustani
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ReminderForm;
