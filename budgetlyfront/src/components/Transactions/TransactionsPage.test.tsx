import { render, screen, waitFor } from '@testing-library/react';
import TransactionsAllPage from './TransactionsPage';
import { describe, it, expect, vi, beforeAll } from 'vitest';
import userEvent from '@testing-library/user-event';
import { JSDOM } from 'jsdom';
import * as api from '../../api';

if (!window.matchMedia) {
  window.matchMedia = () => ({
    matches: false,
    media: '',
    onchange: null,
    addListener: () => {},
    removeListener: () => {},
    addEventListener: () => {},
    removeEventListener: () => {},
    dispatchEvent: () => false,
  });
}

beforeAll(() => {
  if (!globalThis.document) {
    const dom = new JSDOM('<!doctype html><html><body></body></html>');
    globalThis.window = dom.window as any;
    globalThis.document = dom.window.document;
    globalThis.navigator = dom.window.navigator;
  }
});

vi.mock('../../api', () => ({
  fetchIncomes: vi.fn(),
  fetchExpenses: vi.fn(),
  deleteIncome: vi.fn(),
  deleteExpense: vi.fn(),
  fetchValutas: vi.fn(),
}));

vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return {
    ...antd,
    message: { error: vi.fn(), success: vi.fn() },
  };
});

vi.mock('./TransactionForm', () => ({ default: () => <div>Mocked TransactionForm</div> }));
vi.mock('./TransactionEditForm', () => ({ default: () => <div>Mocked TransactionEditForm</div> }));

describe('TransactionsAllPage', () => {
  it('prikazuje praznu tablicu kad nema transakcija', async () => {
    vi.mocked(api.fetchIncomes).mockResolvedValueOnce([]);
    vi.mocked(api.fetchExpenses).mockResolvedValueOnce([]);
    vi.mocked(api.fetchValutas).mockResolvedValueOnce([
      { id: 1, kod: 'EUR', simbol: '€', naziv: '' },
      { id: 2, kod: 'USD', simbol: '$', naziv: '' },
    ]);

    render(<TransactionsAllPage />);

    await waitFor(() => {
      expect(screen.getByText(/Dodaj transakciju/i)).toBeInTheDocument();
      expect(screen.getByText(/Pretraži transakcije/i)).toBeInTheDocument();
      expect(screen.getByText(/Resetiraj/i)).toBeInTheDocument();
    });

    expect(screen.queryByText(/Prihod/)).not.toBeInTheDocument();
    expect(screen.queryByText(/Trošak/)).not.toBeInTheDocument();
  });

  it('prikazuje prihode i troškove u tablici', async () => {
    vi.mocked(api.fetchIncomes).mockResolvedValueOnce([
      {
        id: 1,
        iznos: 100,
        opis: 'Plaća',
        datumTransakcije: '2024-05-01',
        korisnikId: 1,
        prihodKategorija: 'Plaća',
        vrsta: ''
      },
    ]);
    vi.mocked(api.fetchExpenses).mockResolvedValueOnce([
      {
        id: 2,
        iznos: 50,
        opis: 'Namirnice',
        datumTransakcije: '2024-05-02',
        korisnikId: 1,
        trosakKategorija: 'Hrana',
        vrsta: ''
      },
    ]);
    vi.mocked(api.fetchValutas).mockResolvedValueOnce([
      { id: 1, kod: 'EUR', simbol: '€', naziv: '' },
      { id: 2, kod: 'USD', simbol: '$', naziv: '' },
    ]);

    render(<TransactionsAllPage />);

    // Prihod
    await waitFor(() => {
      expect(screen.getAllByText(/Plaća/i).length).toBeGreaterThanOrEqual(1);
      expect(screen.getByText(/100.00/i)).toBeInTheDocument();
      expect(screen.getByText(/Prihod/i)).toBeInTheDocument();
    });

    // Trošak
    expect(screen.getAllByText(/Namirnice/i).length).toBeGreaterThanOrEqual(1);
    expect(screen.getByText(/50.00/i)).toBeInTheDocument();
    expect(screen.getByText(/Trošak/i)).toBeInTheDocument();
  });
});