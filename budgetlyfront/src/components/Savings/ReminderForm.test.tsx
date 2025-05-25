import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ReminderForm from './ReminderForm';
import { describe, expect, it, vi, beforeEach } from 'vitest';
import userEvent from '@testing-library/user-event';
import * as api from '../../api';
import moment from 'moment';
import React from 'react';

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

// Mock react-router-dom
const mockUseParams = vi.fn();
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual<any>('react-router-dom');
  return {
    ...actual,
    useParams: () => mockUseParams(),
  };
});


vi.mock('../../api', () => ({
  createReminder: vi.fn(),
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
          onChange={(e) => {
            const momentDate = moment(e.target.value, 'YYYY-MM-DDTHH:mm');
            if (onChange && momentDate.isValid()) {
              onChange(momentDate);
            }
          }}
        />
      );
    },
  };
});

const mockSavingId = 123;

describe('ReminderForm', () => {
  const mockOnAdded = vi.fn();
  const mockOnCancel = vi.fn();

  beforeEach(() => {
    mockUseParams.mockReturnValue({ id: String(mockSavingId) });
    vi.clearAllMocks();
  });

  it('treba ispravno prikazati formu za unos podsjetnika', () => {
    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);

    expect(screen.getByLabelText(/Naziv podsjetnika/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Opis \(opcionalno\)/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Datum i vrijeme podsjetnika/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Dodaj podsjetnik/i })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /Odustani/i })).toBeInTheDocument();
  });

  it('treba ažurirati polja forme prilikom unosa', async () => {
    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);

    await userEvent.type(screen.getByLabelText(/Naziv podsjetnika/i), 'Novi Podsjetnik');
    expect(screen.getByLabelText(/Naziv podsjetnika/i)).toHaveValue('Novi Podsjetnik');

    await userEvent.type(screen.getByLabelText(/Opis \(opcionalno\)/i), 'Detalji podsjetnika');
    expect(screen.getByLabelText(/Opis \(opcionalno\)/i)).toHaveValue('Detalji podsjetnika');

    const dateTimeInput = screen.getByLabelText(/Datum i vrijeme podsjetnika/i);
    fireEvent.change(dateTimeInput, { target: { value: '2024-08-20T14:30' } }); // ISO format for datetime-local
    
  });

  it('treba pozvati createReminder i onAdded na uspješno submitanje forme', async () => {
    const user = userEvent.setup();
    vi.mocked(api.createReminder).mockResolvedValueOnce({} as any);
    
    
    const { message } = await import('antd');

    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);
    
    
    await user.type(screen.getByLabelText(/Naziv podsjetnika/i), 'Važan Podsjetnik');
    await user.type(screen.getByLabelText(/Opis \(opcionalno\)/i), 'Ne zaboravi!');
    
    const dateInput = screen.getByTestId('date-picker');
    fireEvent.change(dateInput, { target: { value: '2024-09-01T09:00' } });

   
    await user.click(screen.getByRole('button', { name: /Dodaj podsjetnik/i }));

    await waitFor(() => {
      expect(api.createReminder).toHaveBeenCalledTimes(1);
      expect(api.createReminder).toHaveBeenCalledWith({
        stednjaId: mockSavingId,
        naziv: 'Važan Podsjetnik',
        opis: 'Ne zaboravi!',
        datumPodsjetnika: '2024-09-01 09:00',
      });
      expect(mockOnAdded).toHaveBeenCalledTimes(1);
      expect(message.success).toHaveBeenCalledWith('Podsjetnik uspješno dodan');
    });
  });

  it('treba prikazati poruku o grešci ako createReminder ne uspije', async () => {
    const user = userEvent.setup();
    vi.mocked(api.createReminder).mockRejectedValueOnce(new Error('API Error'));
    
    const { message } = await import('antd');

    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);
    
    
    await user.type(screen.getByLabelText(/Naziv podsjetnika/i), 'Neuspješan Podsjetnik');
    
    const dateInput = screen.getByTestId('date-picker');
    fireEvent.change(dateInput, { target: { value: '2024-07-01T10:00' } });

    await user.click(screen.getByRole('button', { name: /Dodaj podsjetnik/i }));

    await waitFor(() => {
      expect(api.createReminder).toHaveBeenCalledTimes(1);
      expect(mockOnAdded).not.toHaveBeenCalled();
      expect(message.error).toHaveBeenCalledWith('Greška pri dodavanju podsjetnika');
    });
  });

  it('treba pozvati onCancel kada se klikne na "Odustani" gumb', async () => {
    const user = userEvent.setup();
    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);
    
    await user.click(screen.getByRole('button', { name: /Odustani/i }));
    expect(mockOnCancel).toHaveBeenCalledTimes(1);
  });

  it('treba prikazati grešku ako savingId nije dostupan', async () => {
    const user = userEvent.setup();
    mockUseParams.mockReturnValue({ id: undefined }); 
    
    const { message } = await import('antd');
    
    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);
    
    await user.type(screen.getByLabelText(/Naziv podsjetnika/i), 'Bilo koji naziv');
    
    const dateInput = screen.getByTestId('date-picker');
    fireEvent.change(dateInput, { target: { value: '2024-07-01T10:00' } });
    
    await user.click(screen.getByRole('button', { name: /Dodaj podsjetnik/i }));

    await waitFor(() => {
      expect(api.createReminder).not.toHaveBeenCalled();
      expect(message.error).toHaveBeenCalledWith('Ne mogu dohvatiti ID štednje');
      expect(mockOnAdded).not.toHaveBeenCalled();
    });
  });

  it('treba prikazati grešku ako savingId je NaN', async () => {
    const user = userEvent.setup();
    mockUseParams.mockReturnValue({ id: 'invalid' }); 
    
    const { message } = await import('antd');
    
    render(<ReminderForm onAdded={mockOnAdded} onCancel={mockOnCancel} />);
    
    await user.type(screen.getByLabelText(/Naziv podsjetnika/i), 'Test naziv');
    
    const dateInput = screen.getByTestId('date-picker');
    fireEvent.change(dateInput, { target: { value: '2024-07-01T10:00' } });
    
    await user.click(screen.getByRole('button', { name: /Dodaj podsjetnik/i }));

    await waitFor(() => {
      expect(api.createReminder).not.toHaveBeenCalled();
      expect(message.error).toHaveBeenCalledWith('Ne mogu dohvatiti ID štednje');
      expect(mockOnAdded).not.toHaveBeenCalled();
    });
  });
});
