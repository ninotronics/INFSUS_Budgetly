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
import { render, screen, fireEvent } from '@testing-library/react';
import TransactionsList from './TransactionsList';

if (!window.getComputedStyle) {
  window.getComputedStyle = () => ({} as CSSStyleDeclaration);
}
import { describe, it, expect, vi } from 'vitest';

describe('TransactionsList', () => {
  it('renders a transaction and calls onEdit', () => {
    const tx = {
      id: 1,
      iznos: 100,
      opis: 'Test opis',
      vrsta: 'PRIHOD',
      prihodKategorija: 'Plaća',
      datumTransakcije: '2024-05-01',
      korisnikId: 1,
    };
    const onEdit = vi.fn();
    render(<TransactionsList transactions={[tx]} onEdit={onEdit} />);
    expect(screen.getByText('Test opis')).toBeInTheDocument();
    fireEvent.click(screen.getByText('Uredi'));
    expect(onEdit).toHaveBeenCalledWith(tx);
  });
}); 