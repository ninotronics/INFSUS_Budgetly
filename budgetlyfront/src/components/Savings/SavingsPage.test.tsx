import { render, screen, waitFor } from '@testing-library/react';
import SavingsPage from './SavingsPage';
import { describe, expect, it, vi, beforeAll } from 'vitest';
import userEvent from '@testing-library/user-event';
import { JSDOM } from 'jsdom';
import * as api from '../../api';


if (!window.matchMedia) {
  window.matchMedia = (query: string) => ({
    matches: false,
    media: query,
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


vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual<any>('react-router-dom');
  return {
    ...actual,
    useParams: () => ({}),
    useNavigate: () => vi.fn(),
  };
});


vi.mock('../../api', () => ({
  fetchSavings: vi.fn(),
  deleteSaving: vi.fn(),
  deleteReminder: vi.fn(),
}));


vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return {
    ...antd,
    message: { error: vi.fn(), success: vi.fn() },
  };
});


vi.mock('./SavingsForm', () => ({ default: () => <div>Mocked SavingsForm</div> }));
vi.mock('./SavingEditForm', () => ({ default: () => <div>Mocked SavingsEditForm</div> }));
vi.mock('./ReminderForm', () => ({ default: () => <div>Mocked ReminderForm</div> }));
vi.mock('./ReminderEditForm', () => ({ default: () => <div>Mocked ReminderEditForm</div> }));

describe('SavingsPage', () => {
  it('prikazuje prazno stanje kad nema štednji', async () => {
    // Mock praznog fetchSavings
    vi.mocked(api.fetchSavings).mockResolvedValueOnce([]);
    render(<SavingsPage />);
    expect(screen.getByText(/Dodaj štednju/i)).toBeInTheDocument();

    // Klikni na tab "Moje štednje"
    await userEvent.click(screen.getByText(/Moje štednje/i));

    await waitFor(() => {
      expect(screen.getByText(/Nemate nijednu štednju/i)).toBeInTheDocument();
    });
  });

  it('prikazuje podatke štednje kad postoji barem jedna štednja', async () => {
    const mockSaving = {
      id: 1,
      naziv: 'Test štednja',
      opis: 'Opis štednje',
      datumKreiranja: '2024-01-01',
      datumKraj: '2024-12-31',
      ciljniIznos: 1000,
      trenutniIznos: 500, 
      korisnikId: 123,    
      podsjetnici: [],
    };
    vi.mocked(api.fetchSavings).mockResolvedValueOnce([mockSaving]);
    render(<SavingsPage />);
    await userEvent.click(screen.getByText(/Moje štednje/i));
    await waitFor(() => {
      expect(screen.getByText(/Test štednja/i)).toBeInTheDocument();
      expect(screen.getByText(/Opis štednje/i)).toBeInTheDocument();
      expect(screen.getByText(/2024-01-01/i)).toBeInTheDocument();
      expect(screen.getByText(/2024-12-31/i)).toBeInTheDocument();
      expect(screen.getByText(/1000/i)).toBeInTheDocument();
    });
  });
});