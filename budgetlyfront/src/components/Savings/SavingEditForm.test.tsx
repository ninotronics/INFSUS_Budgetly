import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import moment from 'moment';
import { Form } from 'antd';
import SavingEditForm from './SavingEditForm';
import { updateSaving } from '../../api';
import { describe, expect, it, vi, beforeEach } from 'vitest';
import { StednjaResponseDTO } from '../../types1';

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


vi.mock('../../api', () => ({
  updateSaving: vi.fn(),
}));


vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return {
    ...antd,
    message: {
      success: vi.fn(),
      error: vi.fn(),
    },
  };
});

describe('SavingsEditForm', () => {
  const mockSaving: StednjaResponseDTO = {
    id: 1,
    naziv: 'Test Štednja',
    opis: 'Test Opis',
    datumKreiranja: '2024-01-01',
    datumKraj: '2024-12-31',
    ciljniIznos: 1000,
    trenutniIznos: 500,
    korisnikId: 1,
    podsjetnici: [],
  };

  const mockOnSaved = vi.fn();
  const mockOnCancel = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('renders form with initial values', () => {
    render(
      <SavingEditForm
        saving={mockSaving}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );

    expect(screen.getByLabelText('Naziv štednje')).toHaveValue('Test Štednja');
    expect(screen.getByLabelText('Opis (opcional)')).toHaveValue('Test Opis');
    expect(screen.getByLabelText('Ciljni iznos')).toHaveValue('1000');
   
  });

  it('calls onCancel when cancel button is clicked', () => {
    render(
      <SavingEditForm
        saving={mockSaving}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );

    fireEvent.click(screen.getByText('Odustani'));
    expect(mockOnCancel).toHaveBeenCalledTimes(1);
  });

  it('submits form with updated values', async () => {
    const user = userEvent.setup(); 
    vi.mocked(updateSaving).mockResolvedValueOnce(mockSaving);

    render(
      <SavingEditForm
        saving={mockSaving}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );

    
    const nazivInput = screen.getByLabelText('Naziv štednje');
    await user.clear(nazivInput);
    await user.type(nazivInput, 'Updated Štednja');

    
    const submitButton = screen.getByText('Spremi izmjene');
    await user.click(submitButton);

    await waitFor(() => {
      expect(updateSaving).toHaveBeenCalledWith(1, expect.objectContaining({
        naziv: 'Updated Štednja',
        opis: 'Test Opis',
        ciljniIznos: 1000,
        korisnikId: 1,
        
        datumKreiranja: expect.stringMatching(/^\d{4}-\d{2}-\d{2}$/),
        datumKraj: expect.stringMatching(/^\d{4}-\d{2}-\d{2}$/),
      }));
      expect(mockOnSaved).toHaveBeenCalledTimes(1);
    });
  });

  it('shows error message when update fails', async () => {
    const user = userEvent.setup();
    vi.mocked(updateSaving).mockRejectedValueOnce(new Error('Update failed'));

    render(
      <SavingEditForm
        saving={mockSaving}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );

    const submitButton = screen.getByText('Spremi izmjene');
    await user.click(submitButton);

    await waitFor(() => {
      expect(updateSaving).toHaveBeenCalled();
      expect(mockOnSaved).not.toHaveBeenCalled();
    });
  });

  it('renders date picker fields with correct initial values', () => {
    render(
      <SavingEditForm
        saving={mockSaving}
        onSaved={mockOnSaved}
        onCancel={mockOnCancel}
      />
    );

    expect(screen.getByLabelText('Datum početka')).toBeInTheDocument();
    expect(screen.getByLabelText('Datum kraja')).toBeInTheDocument();
  });
});
