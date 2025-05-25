// src/components/Savings/ReminderEditForm.tsx
import React from 'react';
import { Form, DatePicker, Input, Button, message, Space } from 'antd';
import moment from 'moment';
import { useParams } from 'react-router-dom';
import { updateReminder } from '../../api';
import { PodsjetnikRequestDTO, PodsjetnikResponseDTO } from '../../types1';

interface Props {
  reminder: PodsjetnikResponseDTO;
  onSaved: () => void;
  onCancel: () => void;
}

const ReminderEditForm: React.FC<Props> = ({ reminder, onSaved, onCancel }) => {
  const [form] = Form.useForm();
  const { id } = useParams<{ id: string }>();
  const savingId = Number(id);

  const initialValues = {
    naziv: reminder.naziv,
    opis: reminder.opis,
    datumPodsjetnika: moment(reminder.datumPodsjetnika),
  };

  const onFinish = async (values: any) => {
    if (!savingId) {
      message.error('Ne mogu dohvatiti ID štednje iz URL-a');
      return;
    }
    const payload: PodsjetnikRequestDTO = {
      stednjaId: savingId,
      naziv: values.naziv,
      opis: values.opis,
      datumPodsjetnika: (values.datumPodsjetnika as moment.Moment).format('YYYY-MM-DD HH:mm'),
    };
    try {
      await updateReminder(reminder.id, payload);
      message.success('Podsjetnik uspješno ažuriran');
      onSaved();
      onCancel();
    } catch (error) {
      console.error(error);
      message.error('Greška pri ažuriranju podsjetnika');
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
        name="datumPodsjetnika"
        label="Datum i vrijeme podsjetnika"
        rules={[{ required: true, message: 'Odaberite datum i vrijeme' }]}
      >
        <DatePicker
          showTime={{ format: 'HH:mm' }}
          format="YYYY-MM-DD HH:mm"
          style={{ width: '100%' }}
        />
      </Form.Item>

      <Form.Item
        name="naziv"
        label="Naziv podsjetnika"
        rules={[{ required: true, message: 'Unesite naziv podsjetnika' }]}
      >
        <Input />
      </Form.Item>

      <Form.Item name="opis" label="Opis (opcionalno)">
        <Input.TextArea rows={2} />
      </Form.Item>

      <Form.Item>
        <Space>
          <Button type="primary" htmlType="submit">
            Spremi izmjene
          </Button>
          <Button onClick={onCancel}>Odustani</Button>
        </Space>
      </Form.Item>
    </Form>
  );
};

export default ReminderEditForm;
