/// <reference types="cypress" />

describe('Savings stranica', () => {
  it('Stranica se uspješno učitava', () => {
    cy.visit('/pocetna');
    cy.contains('Štednje').click();
    cy.url().should('include', '/Savings');
    cy.contains(/Dodaj štednju/i, { timeout: 10000 }).should('be.visible');
  });

  it('Korisnik može dodati i vidjeti novu štednju', () => {
    cy.visit('/pocetna');
    cy.contains('Štednje').click();
    cy.url().should('include', '/Savings');
    cy.contains(/Dodaj štednju/i, { timeout: 10000 }).should('be.visible');

    // Klikni na tab "Dodaj štednju" ako nije aktivan
    cy.contains(/Dodaj štednju/i).click();

    // Ispuni formu za novu štednju
    const naziv = 'Test štednja ';
    cy.get('input').first().clear().type(naziv);
    cy.get('textarea').first().type('Ovo je test štednje');
    // Datum kreiranja (danas)
    cy.get('input').eq(1).click();
    const today = new Date().getDate();
    cy.get('.ant-picker-dropdown').should('be.visible');
    cy.get('.ant-picker-cell-inner').contains(today).click();
    // Klik izvan da zatvori popup
    cy.get('body').click(0,0);
    // Datum kraja (isti kao datum kreiranja, koristi vidljivu ćeliju)
    cy.get('input').eq(2).click();
    cy.get('.ant-picker-dropdown').should('be.visible');
    cy.get('.ant-picker-cell-inner')
      .filter(':visible')
      .contains(today)
      .click({ force: true });
    cy.get('input').eq(3).type('1000');
    cy.get('input').eq(4).type('100');
    cy.contains('button', 'Spremi štednju').click();

    // Pričekaj malo da se poruka pojavi
    cy.wait(500);
    // Prebaci se na tab "Moje štednje"
    cy.contains(/Moje štednje/i).click();

    // Listaj po štednjama dok ne pronađeš novu (provjerava Naziv: ...)
    function findStednja(tryCount = 0) {
      cy.contains(/^Naziv:/).parent().invoke('text').then(text => {
        if (text.includes(naziv)) {
          expect(text).to.include(naziv);
        } else if (tryCount < 20) {
          cy.get('button').contains('Sljedeća').then($btn => {
            if (!$btn.is(':disabled')) {
              cy.wrap($btn).click();
              findStednja(tryCount + 1);
            } else {
              throw new Error('Nova štednja nije pronađena u listi!');
            }
          });
        } else {
          throw new Error('Previše pokušaja, štednja nije pronađena!');
        }
      });
    }
    findStednja();
  });

  it('Korisnik može dodati podsjetnik na štednju', () => {
    const naziv = 'Test štednja ';

    cy.visit('/pocetna');
    cy.contains('Štednje').click();
    cy.url().should('include', '/Savings');
    cy.contains(/Moje štednje/i).click();

    // Listaj do štednje
    function findStednja(tryCount = 0, callback) {
      cy.contains(/^Naziv:/).parent().invoke('text').then(text => {
        if (text.includes(naziv)) {
          expect(text).to.include(naziv);
          callback && callback();
        } else if (tryCount < 20) {
          cy.get('button').contains('Sljedeća').then($btn => {
            if (!$btn.is(':disabled')) {
              cy.wrap($btn).click();
              findStednja(tryCount + 1, callback);
            } else {
              throw new Error('Štednja nije pronađena!');
            }
          });
        } else {
          throw new Error('Previše pokušaja, štednja nije pronađena!');
        }
      });
    }
    findStednja(0, () => {
      // Dodaj podsjetnik
      cy.contains('+ Dodaj novi podsjetnik').click();

      // Unutar modala s naslovom "Dodaj novi podsjetnik":
      cy.get('.ant-modal').contains('Dodaj novi podsjetnik').parents('.ant-modal').within(() => {
        cy.get('input').first().type('Podsjetnik test');
        cy.get('textarea').first().type('Ovo je test podsjetnika');
        // Ručni unos datuma i vremena u ispravnom formatu
        const now = new Date();
        const dateStr = now.toLocaleDateString('en-CA'); // 'YYYY-MM-DD'
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        const dateTimeStr = `${dateStr} ${hours}:${minutes}`;
        cy.get('input').eq(1).clear().type(dateTimeStr);

        cy.contains('button', 'Dodaj podsjetnik').click();
      });

      // Provjeri da se podsjetnik pojavio u tablici
      cy.contains('Podsjetnik test', { timeout: 10000 }).should('be.visible');
    });
  });
});

describe('Dodavanje štednje', () => {
  it('Korisnik može doći do forme za dodavanje štednje', () => {
    cy.visit('/pocetna');
    cy.contains('Štednje').click();
    cy.url().should('include', '/Savings');
    cy.contains(/Dodaj štednju/i, { timeout: 10000 }).should('be.visible');
  });
}); 