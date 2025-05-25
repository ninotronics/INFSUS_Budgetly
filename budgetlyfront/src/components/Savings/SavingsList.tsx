// src/components/Savings/SavingsList.tsx
import React from 'react';
import { Table, Button, Popconfirm } from 'antd';
import moment from 'moment';
import { StednjaResponseDTO } from '../../types1';

interface Props {
  savings: StednjaResponseDTO[];
  onEdit: (saving: StednjaResponseDTO) => void;
  onDelete: (id: number) => void;
}

const SavingsList: React.FC<Props> = ({ savings, onEdit, onDelete }) => (
  <Table<StednjaResponseDTO>
    dataSource={savings}
    rowKey="id"
    pagination={{ pageSize: 10 }}
  >
    <Table.Column title="Naziv" dataIndex="naziv" key="naziv" />
    <Table.Column title="Opis" dataIndex="opis" key="opis" />
    <Table.Column
      title="Datum kreiranja"
      dataIndex="datumKreiranja"
      key="datumKreiranja"
      render={d => moment(d).format('YYYY-MM-DD')}
    />
    <Table.Column
      title="Datum kraj"
      dataIndex="datumKraj"
      key="datumKraj"
      render={d => moment(d).format('YYYY-MM-DD')}
    />
    <Table.Column title="Ciljni iznos" dataIndex="ciljniIznos" key="ciljniIznos" />
    <Table.Column<StednjaResponseDTO>
      title="Akcije"
      key="actions"
      render={(_, record) => (
        <>
          <Button type="link" onClick={() => onEdit(record)}>
            Uredi
          </Button>
          <Popconfirm
            title="Jeste li sigurni da želite obrisati ovu štednju?"
            onConfirm={() => onDelete(record.id)}
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
);

export default SavingsList;
