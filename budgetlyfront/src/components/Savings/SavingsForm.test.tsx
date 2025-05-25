import { render, screen } from '@testing-library/react';
import SavingsForm from './SavingsForm';
import { describe, it, expect } from 'vitest';

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

describe('SavingsForm', () => {
  it('renders', () => {
    render(<SavingsForm onSaved={() => {}} />);
    expect(screen.getByLabelText('Naziv štednje')).toBeInTheDocument();
    expect(screen.getByText('Spremi štednju')).toBeInTheDocument();
  });
}); 