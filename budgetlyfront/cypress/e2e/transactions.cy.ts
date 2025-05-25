/// <reference types="cypress" />

describe('Transakcije stranica', () => {
  it('Stranica se uspješno učitava i prikazuje osnovne elemente', () => {
    cy.visit('/pocetna');
    cy.contains('Transakcije').click();
    cy.url().should('include', '/Transakcije');
    cy.contains('Dodaj transakciju', { timeout: 10000 }).should('be.visible');
    cy.contains('Pretraži transakcije').should('be.visible');
    cy.contains('Resetiraj').should('be.visible');
    cy.get('table').should('be.visible');
  });

  it('Može otvoriti i zatvoriti modal za dodavanje transakcije', () => {
    cy.visit('/pocetna');
    cy.contains('Transakcije').click();
    cy.contains('Dodaj transakciju').click();
    cy.get('.ant-modal:visible').contains('Nova transakcija').should('be.visible');
    cy.get('.ant-modal:visible .ant-modal-close').click();
    cy.get('.ant-modal:visible').should('not.exist');
  });

  it('Može unijeti i spremiti novu transakciju', () => {
    cy.visit('/pocetna');
    cy.contains('Transakcije').click();
    cy.contains('Dodaj transakciju').click();
    cy.get('.ant-modal:visible').contains('Nova transakcija').should('be.visible');

    // Unos podataka u modal (unutar within)
    cy.get('.ant-modal:visible').within(() => {
      cy.contains('Vrsta transakcije')
        .closest('.ant-form-item')
        .find('input[value="TROSAK"]')
        .click({ force: true });

      cy.contains('Iznos')
        .closest('.ant-form-item')
        .find('input')
        .clear({ force: true })
        .type('120', { force: true });

      cy.contains('Opis')
        .closest('.ant-form-item')
        .find('textarea')
        .type('Mobitel', { force: true });

      // Samo otvori dropdown unutar within
      cy.contains('Kategorija')
        .closest('.ant-form-item')
        .find('.ant-select')
        .click();
    });

    // Izvan within scope-a: biranje opcije iz dropdowna
    cy.wait(500);
    cy.get('.ant-select-dropdown:not([aria-hidden="true"]) .ant-select-item-option-content')
      .contains('OSTALO')
      .click({ force: true });

    // Povratak u modal za klik na gumb
    cy.get('.ant-modal:visible').contains('button', 'Dodaj transakciju').click({ force: true });

    cy.get('.ant-modal:visible').should('not.exist');

    cy.contains('Mobitel', { timeout: 10000 }).should('be.visible');
  });
});