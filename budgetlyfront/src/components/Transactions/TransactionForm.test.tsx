import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import TransactionForm from './TransactionForm';
import { vi, describe, it, expect } from 'vitest';

vi.mock('../../api', () => ({
  createIncome: vi.fn(),
  createExpense: vi.fn(),
}));
vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return { ...antd, message: { success: vi.fn(), error: vi.fn() } };
});

if (!window.matchMedia) {
  window.matchMedia = () => ({
    matches: false, media: '', onchange: null,
    addListener: () => {}, removeListener: () => {},
    addEventListener: () => {}, removeEventListener: () => {},
    dispatchEvent: () => false,
  });
}

describe('TransactionForm', () => {
  it('submits income', async () => {
    const onSaved = vi.fn();
    const { createIncome } = await import('../../api');
    vi.mocked(createIncome).mockResolvedValueOnce({
      id: 1, iznos: 100, opis: 'Test', vrsta: 'PRIHOD',
      prihodKategorija: 'PLAĆA', datumTransakcije: '2024-01-01', korisnikId: 1,
    });
    render(<TransactionForm onSaved={onSaved} currency="EUR" exchangeRate={1} />);
    fireEvent.click(screen.getByLabelText('Prihod'));
    fireEvent.change(screen.getByLabelText('Iznos'), { target: { value: '100' } });
    fireEvent.change(screen.getByLabelText('Opis'), { target: { value: 'Test' } });
    // Kategorija preko dropdowna
    const kategorijaSelect = screen.getByLabelText('Kategorija');
    fireEvent.mouseDown(kategorijaSelect); // open dropdown
    const option = screen.getAllByText('PLAĆA');
    fireEvent.click(option[option.length - 1]);
    fireEvent.click(screen.getByText('Dodaj transakciju'));
    await waitFor(() => {
      expect(createIncome).toHaveBeenCalledWith(expect.objectContaining({ iznos: 100, opis: 'Test', prihodKategorija: 'PLAĆA' }));
      expect(onSaved).toHaveBeenCalled();
    });
  });

  it('submits expense', async () => {
    const onSaved = vi.fn();
    const { createExpense } = await import('../../api');
    vi.mocked(createExpense).mockResolvedValueOnce({
      id: 2, iznos: 50, opis: 'Test trošak', vrsta: 'TROSAK',
      trosakKategorija: 'HRANA', datumTransakcije: '2024-01-01', korisnikId: 1,
    });
    render(<TransactionForm onSaved={onSaved} currency="EUR" exchangeRate={1} />);
    fireEvent.click(screen.getByLabelText('Trošak'));
    fireEvent.change(screen.getByLabelText('Iznos'), { target: { value: '50' } });
    fireEvent.change(screen.getByLabelText('Opis'), { target: { value: 'Test trošak' } });
    // Kategorija preko dropdowna
    const kategorijaSelect = screen.getByLabelText('Kategorija');
    fireEvent.mouseDown(kategorijaSelect); // open dropdown
    const option = screen.getAllByText('HRANA');
    fireEvent.click(option[option.length - 1]);
    fireEvent.click(screen.getByText('Dodaj transakciju'));
    await waitFor(() => {
      expect(createExpense).toHaveBeenCalledWith(expect.objectContaining({ iznos: 50, opis: 'Test trošak', trosakKategorija: 'HRANA' }));
      expect(onSaved).toHaveBeenCalled();
    });
  });
});
