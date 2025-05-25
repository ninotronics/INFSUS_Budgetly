/// <reference types="cypress" />

describe('Početna stranica', () => {
  it('Sidebar prikazuje naziv aplikacije', () => {
    cy.visit('/pocetna');
    cy.contains('Budgetly').should('be.visible');
  });
}); 