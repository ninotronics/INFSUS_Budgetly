// Test suite for TransactionEditForm
import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import TransactionEditForm from './TransactionEditForm';
import { PrihodEnum, TrosakEnum, PrihodResponseDTO, TrosakResponseDTO } from '../../types1';

vi.mock('../../api', () => ({
  updateIncome: vi.fn(),
  updateExpense: vi.fn(),
}));
vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return { ...antd, message: { success: vi.fn(), error: vi.fn() } };
});

// Fix for Ant Design's use of window.matchMedia in tests
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

const mockOnSaved = vi.fn();
const mockOnCancel = vi.fn();

const baseIncome: PrihodResponseDTO = {
  id: 1,
  opis: 'Stari opis',
  iznos: 100,
  vrsta: 'PRIHOD',
  prihodKategorija: PrihodEnum.PLAĆA,
  datumTransakcije: '2024-01-01',
  korisnikId: 1,
};
const baseExpense: TrosakResponseDTO = {
  id: 2,
  opis: 'Stari opis troška',
  iznos: 50,
  vrsta: 'TROSAK',
  trosakKategorija: TrosakEnum.HRANA,
  datumTransakcije: '2024-01-01',
  korisnikId: 1,
};

describe('TransactionEditForm', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('ažurira prihod i poziva updateIncome s ispravnim podacima', async () => {
    const { updateIncome } = await import('../../api');
    vi.mocked(updateIncome).mockResolvedValueOnce({
      id: 1,
      opis: 'Novi opis',
      iznos: 200,
      vrsta: 'PRIHOD',
      prihodKategorija: PrihodEnum.PRODAJA,
      datumTransakcije: '2024-01-01',
      korisnikId: 1,
    });
    render(
      <TransactionEditForm
        tx={baseIncome}
        currency="EUR"
        exchangeRate={1}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );
    const user = userEvent.setup();
    // Promijeni iznos
    const iznosInput = screen.getByLabelText(/Iznos/i);
    await user.clear(iznosInput);
    await user.type(iznosInput, '200');
    // Promijeni opis
    const opisInput = screen.getByLabelText(/Opis/i);
    await user.clear(opisInput);
    await user.type(opisInput, 'Novi opis');
    // Promijeni kategoriju (dropdown)
    const kategorijaSelect = screen.getByLabelText(/Kategorija/i);
    await user.click(kategorijaSelect);
    const newCategory = PrihodEnum.PRODAJA;
    const options = screen.getAllByText(newCategory);
    await user.click(options[options.length - 1]);
    // Submit
    await user.click(screen.getByRole('button', { name: /Spremi/i }));
    await waitFor(() => {
      expect(updateIncome).toHaveBeenCalledWith(1, expect.objectContaining({
        iznos: 200,
        opis: 'Novi opis',
        prihodKategorija: newCategory,
      }));
      expect(mockOnSaved).toHaveBeenCalled();
      expect(mockOnCancel).toHaveBeenCalled();
    });
  });

  it('ažurira trošak i poziva updateExpense s ispravnim podacima', async () => {
    const { updateExpense } = await import('../../api');
    vi.mocked(updateExpense).mockResolvedValueOnce({
      id: 2,
      opis: 'Novi trošak',
      iznos: 75,
      vrsta: 'TROSAK',
      trosakKategorija: TrosakEnum.REŽIJE,
      datumTransakcije: '2024-01-01',
      korisnikId: 1,
    });
    render(
      <TransactionEditForm
        tx={baseExpense}
        currency="EUR"
        exchangeRate={1}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );
    const user = userEvent.setup();
    // Promijeni iznos
    const iznosInput = screen.getByLabelText(/Iznos/i);
    await user.clear(iznosInput);
    await user.type(iznosInput, '75');
    // Promijeni opis
    const opisInput = screen.getByLabelText(/Opis/i);
    await user.clear(opisInput);
    await user.type(opisInput, 'Novi trošak');
    // Promijeni kategoriju (dropdown)
    const kategorijaSelect = screen.getByLabelText(/Kategorija/i);
    await user.click(kategorijaSelect);
    const newCategory = TrosakEnum.REŽIJE;
    const options = screen.getAllByText(newCategory);
    await user.click(options[options.length - 1]);
    // Submit
    await user.click(screen.getByRole('button', { name: /Spremi/i }));
    await waitFor(() => {
      expect(updateExpense).toHaveBeenCalledWith(2, expect.objectContaining({
        iznos: 75,
        opis: 'Novi trošak',
        trosakKategorija: newCategory,
      }));
      expect(mockOnSaved).toHaveBeenCalled();
      expect(mockOnCancel).toHaveBeenCalled();
    });
  });

  it('poziva onCancel kada se klikne Odustani', async () => {
    render(
      <TransactionEditForm
        tx={baseIncome}
        currency="EUR"
        exchangeRate={1}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );
    const user = userEvent.setup();
    await user.click(screen.getByText(/Odustani/i));
    expect(mockOnCancel).toHaveBeenCalled();
  });
});