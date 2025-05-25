import React from 'react';
import { Table, Button, Tag } from 'antd';
import moment from 'moment';
import { TransactionResponseDTO } from '../../types1';

interface Props {
  transactions: TransactionResponseDTO[];
  onEdit: (tx: TransactionResponseDTO) => void;
}

const TransactionsList: React.FC<Props> = ({ transactions, onEdit }) => (
  <Table<TransactionResponseDTO>
    dataSource={transactions}
    rowKey="id"
    pagination={{ pageSize: 10 }}
  >
    <Table.Column<TransactionResponseDTO>
      title="Datum"
      dataIndex="datumTransakcije"
      key="datum"
      render={d => moment(d).format('YYYY-MM-DD')}
    />
    <Table.Column<TransactionResponseDTO>
      title="Iznos"
      dataIndex="iznos"
      key="iznos"
    />
    <Table.Column<TransactionResponseDTO>
      title="Vrsta"
      dataIndex="vrsta"
      key="vrsta"
      render={v =>
        v === 'PRIHOD' ? <Tag color="green">Prihod</Tag> : <Tag color="red">Trošak</Tag>
      }
    />
    <Table.Column<TransactionResponseDTO>
      title="Kategorija"
      key="kategorija"
      render={(_, record) =>
        record.vrsta === 'PRIHOD'
          ? (record as any).prihodKategorija
          : (record as any).trosakKategorija
      }
    />
    <Table.Column<TransactionResponseDTO>
      title="Opis"
      dataIndex="opis"
      key="opis"
    />
    <Table.Column<TransactionResponseDTO>
      title="Akcije"
      key="actions"
      render={(_, record) => (
        <Button
          type="link"
          onClick={e => {
            e.stopPropagation();
            onEdit(record);
          }}
        >
          Uredi
        </Button>
      )}
    />
  </Table>
);

export default TransactionsList;