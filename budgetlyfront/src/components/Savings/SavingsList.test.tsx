import React from 'react';
import { render } from '@testing-library/react';
import SavingsList from './SavingsList';
import { StednjaResponseDTO } from '../../types1';
import { describe, it, expect, vi } from 'vitest';

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

describe('SavingsList', () => {
  const mockSavings: StednjaResponseDTO[] = [
    {
      id: 1,
      naziv: 'Test štednja',
      opis: 'Test opis',
      datumKreiranja: '2024-01-01',
      datumKraj: '2024-12-31',
      ciljniIznos: 1000,
      trenutniIznos: 500,
      korisnikId: 1,
      podsjetnici: [],
    },
  ];

  const mockOnEdit = vi.fn();
  const mockOnDelete = vi.fn();

  it('renders without crashing', () => {
    const { container } = render(
      <SavingsList 
        savings={mockSavings} 
        onEdit={mockOnEdit} 
        onDelete={mockOnDelete} 
      />
    );
    
    expect(container).toBeInTheDocument();
  });

  it('renders with empty savings array', () => {
    const { container } = render(
      <SavingsList 
        savings={[]} 
        onEdit={mockOnEdit} 
        onDelete={mockOnDelete} 
      />
    );
    
    expect(container).toBeInTheDocument();
  });
});
