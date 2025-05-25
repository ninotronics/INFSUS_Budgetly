// src/components/Savings/SavingsPage.tsx
import React, { useEffect, useState } from 'react';
import {
  Tabs,
  Card,
  Button,
  Space,
  Table,
  Modal,
  Popconfirm,
  message,
  Typography,
} from 'antd';
import moment from 'moment';
import { useParams, useNavigate } from 'react-router-dom';
import { StednjaResponseDTO, PodsjetnikResponseDTO } from '../../types1';
import { fetchSavings, deleteSaving, deleteReminder } from '../../api';
import SavingForm from './SavingsForm';
import SavingsEditForm from './SavingEditForm';
import ReminderForm from './ReminderForm';
import ReminderEditForm from './ReminderEditForm';

const { TabPane } = Tabs;
const { Title, Text } = Typography;

const SavingsPage: React.FC = () => {
  
  const { id: paramId } = useParams<{ id: string }>();
  const navigate = useNavigate();

  
  const [activeKey, setActiveKey] = useState<'1' | '2'>(paramId ? '2' : '1');

  const [savings, setSavings] = useState<StednjaResponseDTO[]>([]);
  const [index, setIndex] = useState(0);

  const [editSaving, setEditSaving] = useState<StednjaResponseDTO | null>(null);
  const [editSavingVisible, setEditSavingVisible] = useState(false);

  const [addRemVisible, setAddRemVisible] = useState(false);
  const [editReminder, setEditReminder] = useState<PodsjetnikResponseDTO | null>(null);
  const [editReminderVisible, setEditReminderVisible] = useState(false);

  
  const loadSavings = async () => {
    try {
      const data = await fetchSavings();
      const sorted = data.sort((a, b) =>
        moment(a.datumKreiranja).diff(moment(b.datumKreiranja))
      );
      setSavings(sorted);
      if (paramId) {
        const idNum = parseInt(paramId, 10);
        const idx = sorted.findIndex(s => s.id === idNum);
        setIndex(idx >= 0 ? idx : 0);
      } else {
        setIndex(0);
      }
    } catch {
      message.error('Greška pri dohvaćanju štednji');
    }
  };

  useEffect(() => {
    loadSavings();
  }, [paramId]);

  
  useEffect(() => {
    if (activeKey === '2' && savings.length > 0) {
      const currId = savings[index].id;
      navigate(`/Savings/${currId}`, { replace: true });
    }
  }, [index, activeKey, savings, navigate]);

  const curr = savings[index];

  
  const handleSavedCreate = () => {
    message.success('Nova štednja dodana');
    loadSavings();
    setActiveKey('2');
  };

  
  const prev = () => setIndex(i => Math.max(i - 1, 0));
  const next = () => setIndex(i => Math.min(i + 1, savings.length - 1));

  
  const onEditSaving = () => {
    if (curr) {
      setEditSaving(curr);
      setEditSavingVisible(true);
    }
  };
  const onDeleteSaving = async () => {
    if (curr) {
      await deleteSaving(curr.id);
      message.success('Štednja obrisana');
      loadSavings();
    }
  };

  
  const onAddReminder = () => setAddRemVisible(true);
  const onCloseAddRem = () => setAddRemVisible(false);
  const onEditReminder = (rem: PodsjetnikResponseDTO) => {
    setEditReminder(rem);
    setEditReminderVisible(true);
  };
  const onCloseEditRem = () => setEditReminderVisible(false);
  const onDeleteReminder = async (remId: number) => {
    try {
      await deleteReminder(remId);
      message.success('Podsjetnik uklonjen');
      loadSavings();
    } catch {
      message.error('Greška pri brisanju podsjetnika');
    }
  };

  return (
    <Tabs activeKey={activeKey} onChange={k => setActiveKey(k as '1' | '2')}>
      {/* 1. Dodaj štednju */}
      <TabPane tab="Dodaj štednju" key="1">
        <SavingForm onSaved={handleSavedCreate} />
      </TabPane>

      {/* 2. Moje štednje */}
      <TabPane tab="Moje štednje" key="2">
        {!curr ? (
          <div>Nemate nijednu štednju.</div>
        ) : (
          <Card
            title={<Title level={4}>Moja štednja</Title>}
            extra={
              <Space>
                <Button onClick={prev} disabled={index === 0}>Prethodna</Button>
                <Button onClick={next} disabled={index === savings.length - 1}>Sljedeća</Button>
              </Space>
            }
          >
            <Space direction="vertical" style={{ width: '100%' }}>
              <Text><b>Naziv:</b> {curr.naziv}</Text>
              <Text><b>Opis:</b> {curr.opis}</Text>
              <Text><b>Datum početka:</b> {curr.datumKreiranja}</Text>
              <Text><b>Datum kraj:</b> {curr.datumKraj}</Text>
              <Text><b>Ciljni iznos:</b> {curr.ciljniIznos}</Text>
              {/* <Text><b>Trenutni iznos:</b> {curr.trenutniIznos}</Text> */}

              <Space>
                <Button type="primary" onClick={onEditSaving}>Uredi</Button>
                <Button danger onClick={onDeleteSaving}>Izbriši</Button>
              </Space>

              <Title level={5} style={{ marginTop: 16 }}>Podsjetnici</Title>
              <Button
                type="dashed"
                block
                style={{ marginBottom: 16 }}
                onClick={onAddReminder}
              >
                + Dodaj novi podsjetnik
              </Button>

              <Table<PodsjetnikResponseDTO>
                dataSource={curr.podsjetnici}
                rowKey="id"
                pagination={false}
              >
                <Table.Column title="Naziv" dataIndex="naziv" key="naziv" />
                <Table.Column title="Opis" dataIndex="opis" key="opis" />
                {/* <Table.Column
                  title="Obaviješten"
                  dataIndex="obavijesten"
                  key="obavijesten"
                  render={v => v ? 'Da' : 'Ne'}
                /> */}
                <Table.Column
                  title="Datum podsjetnika"
                  dataIndex="datumPodsjetnika"
                  key="datumPodsjetnika"
                />
                <Table.Column<PodsjetnikResponseDTO>
                  title="Akcije"
                  key="actions"
                  render={(_: any, rem: PodsjetnikResponseDTO) => (
                    <Space>
                      <Button
                        type="link"
                        disabled={rem.obavijesten}
                        onClick={() => onEditReminder(rem)}
                      >
                        Uredi
                      </Button>
                      <Popconfirm
                        title="Jeste li sigurni da želite ukloniti podsjetnik?"
                        onConfirm={() => onDeleteReminder(rem.id)}
                        okText="Da"
                        cancelText="Ne"
                      >
                        <Button type="link" danger>
                          Ukloni
                        </Button>
                      </Popconfirm>
                    </Space>
                  )}
                />
              </Table>
            </Space>
          </Card>
        )}

        {/* Modals */}
        <Modal
          title="Uredi štednju"
          visible={editSavingVisible}
          footer={null}
          onCancel={() => setEditSavingVisible(false)}
          width={600}
        >
          {editSaving && (
            <SavingsEditForm
              saving={editSaving}
              onSaved={() => { setEditSavingVisible(false); loadSavings(); }}
              onCancel={() => setEditSavingVisible(false)}
            />
          )}
        </Modal>

        <Modal
          title="Dodaj novi podsjetnik"
          visible={addRemVisible}
          footer={null}
          onCancel={onCloseAddRem}
          width={500}
        >
          {curr && (
            <ReminderForm
              onAdded={() => { onCloseAddRem(); loadSavings(); }}
              onCancel={onCloseAddRem}
            />
          )}
        </Modal>

        <Modal
          title="Uredi podsjetnik"
          visible={editReminderVisible}
          footer={null}
          onCancel={() => setEditReminderVisible(false)}
          width={500}
        >
          {editReminder && curr && (
            <ReminderEditForm
              reminder={editReminder}
              onSaved={() => { setEditReminderVisible(false); loadSavings(); }}
              onCancel={() => setEditReminderVisible(false)}
            />
          )}
        </Modal>
      </TabPane>
    </Tabs>
  );
};

export default SavingsPage;
