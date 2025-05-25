import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import moment from 'moment';
import ReminderEditForm from './ReminderEditForm';
import { updateReminder } from '../../api';
import { describe, it, expect, vi, beforeEach } from 'vitest';

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


vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual<any>('react-router-dom');
  return {
    ...actual,
    useParams: () => ({ id: '123' }),
  };
});


vi.mock('../../api', () => ({
  updateReminder: vi.fn(),
}));


vi.mock('antd', async () => {
  const antd = await vi.importActual<any>('antd');
  return {
    ...antd,
    message: {
      success: vi.fn(),
      error: vi.fn(),
    },
    DatePicker: ({ onChange, ...props }: any) => {
      return (
        <input
          {...props}
          data-testid="date-picker"
          type="datetime-local"
          onChange={(e) => {
            const val = e.target.value;
            if (onChange) {
              onChange(moment(val));
            }
          }}
        />
      );
    },
  };
});

describe('ReminderEditForm', () => {
  const mockReminder = {
    id: 1,
    naziv: 'Test Podsjetnik',
    opis: 'Test opis',
    datumPodsjetnika: '2025-05-25 14:00',
    stednjaId: 123,
    obavijesten: false,
  };

  const mockOnSaved = vi.fn();
  const mockOnCancel = vi.fn();

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('renders form with initial values', () => {
    render(
      <ReminderEditForm 
        reminder={mockReminder} 
        onSaved={mockOnSaved} 
        onCancel={mockOnCancel} 
      />
    );

    expect(screen.getByLabelText(/Naziv podsjetnika/i)).toHaveValue('Test Podsjetnik');
    expect(screen.getByLabelText(/Opis \(opcionalno\)/i)).toHaveValue('Test opis');
    expect(screen.getByLabelText(/Datum i vrijeme podsjetnika/i)).toBeInTheDocument();
  });

  it('calls updateReminder and callbacks on form submit', async () => {
    const user = userEvent.setup();
    vi.mocked(updateReminder).mockResolvedValueOnce({
      id: 1,
      naziv: 'Updated Podsjetnik',
      opis: 'Test opis',
      datumPodsjetnika: '2025-05-25 14:00',
      obavijesten: false,
    });
    const { message } = await import('antd');

    render(
      <ReminderEditForm 
        reminder={mockReminder} 
        onSaved={mockOnSaved} 
        onCancel={mockOnCancel} 
      />
    );

   
    const nazivInput = screen.getByLabelText(/Naziv podsjetnika/i);
    await user.clear(nazivInput);
    await user.type(nazivInput, 'Updated Podsjetnik');

   
    await user.click(screen.getByText(/Spremi izmjene/i));

    await waitFor(() => {
      expect(updateReminder).toHaveBeenCalledWith(1, expect.objectContaining({
        naziv: 'Updated Podsjetnik',
        opis: 'Test opis',
        stednjaId: 123,
        datumPodsjetnika: expect.stringMatching(/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/),
      }));
      expect(mockOnSaved).toHaveBeenCalledTimes(1);
      expect(mockOnCancel).toHaveBeenCalledTimes(1);
      expect(message.success).toHaveBeenCalledWith('Podsjetnik uspješno ažuriran');
    });
  });

  it('calls onCancel when cancel button is clicked', async () => {
    const user = userEvent.setup();
    render(
      <ReminderEditForm 
        reminder={mockReminder} 
        onSaved={mockOnSaved} 
        onCancel={mockOnCancel} 
      />
    );

    await user.click(screen.getByText(/Odustani/i));
    expect(mockOnCancel).toHaveBeenCalledTimes(1);
  });

  it('shows error message when update fails', async () => {
    const user = userEvent.setup();
    vi.mocked(updateReminder).mockRejectedValueOnce(new Error('API Error'));
    const { message } = await import('antd');

    render(
      <ReminderEditForm 
        reminder={{...mockReminder, obavijesten: false}}
        onSaved={mockOnSaved} 
        onCancel={mockOnCancel} 
      />
    );

    await user.click(screen.getByText(/Spremi izmjene/i));

    await waitFor(() => {
      expect(updateReminder).toHaveBeenCalled();
      expect(mockOnSaved).not.toHaveBeenCalled();
      expect(message.error).toHaveBeenCalledWith('Greška pri ažuriranju podsjetnika');
    });
  });
});
