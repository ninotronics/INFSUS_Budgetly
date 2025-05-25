package INFSUS.repository;

import INFSUS.model.Korisnik;
import INFSUS.model.Podsjetnik;
import INFSUS.model.Stednja;
import INFSUS.model.Valuta;
import INFSUS.model.Uloga;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PodsjetnikRepoTest {

    @Autowired
    private PodsjetnikRepo podsjetnikRepo;

    @Autowired
    private StednjaRepo stednjaRepo;

    @Autowired
    private KorisnikRepo korisnikRepo;

    @Autowired
    private ValutaRepo valutaRepo;

    @Autowired
    private UlogaRepo ulogaRepo;

    @Test
    void testSaveAndFindById() {
        // 1. Kreiraj i spremi ulogu
        Uloga uloga = new Uloga();
        uloga.setNaziv("KORISNIK");
        uloga = ulogaRepo.save(uloga);

        // 2. Kreiraj i spremi valutu
        Valuta valuta = new Valuta();
        valuta.setNaziv("Euro");
        valuta.setKod("EUR");
        valuta.setSimbol("€");
        valuta.setTecajPremaBazi(new java.math.BigDecimal("7.53450"));
        valuta = valutaRepo.save(valuta);

        // 3. Kreiraj i spremi korisnika
        Korisnik korisnik = new Korisnik();
        korisnik.setIme("Test");
        korisnik.setPrezime("Korisnik");
        korisnik.setEmail("test@example.com");
        korisnik.setKorisnickoIme("testuser");
        korisnik.setHashLozinka("hash");
        korisnik.setUkupniIznos(new java.math.BigDecimal("100.00"));
        korisnik.setOsnovniIznos(new java.math.BigDecimal("50.00"));
        korisnik.setDatumRodenja(java.time.LocalDate.of(2000, 1, 1));
        korisnik.setUloga(uloga);
        korisnik.setValuta(valuta);
        korisnik = korisnikRepo.save(korisnik);

        // 4. Kreiraj i spremi stednju povezanu s korisnikom
        Stednja stednja = new Stednja();
        stednja.setNaziv("Štednja za auto");
        stednja.setOpis("Opis štednje");
        stednja.setDatumKreiranja(java.time.LocalDate.now());
        stednja.setDatumKraj(java.time.LocalDate.now().plusMonths(6));
        stednja.setCiljniIznos(new java.math.BigDecimal("5000.00"));
        stednja.setTrenutniIznos(new java.math.BigDecimal("100.00"));
        stednja.setKorisnik(korisnik);
        stednja = stednjaRepo.save(stednja);

        // 5. Kreiraj i spremi podsjetnik
        Podsjetnik podsjetnik = new Podsjetnik();
        podsjetnik.setNaziv("Podsjetnik 1");
        podsjetnik.setOpis("Opis podsjetnika");
        podsjetnik.setDatumPodsjetnika(java.time.LocalDateTime.now());
        podsjetnik.setObavijesten(false);
        podsjetnik.setStednja(stednja);
        Podsjetnik saved = podsjetnikRepo.save(podsjetnik);
        Podsjetnik found = podsjetnikRepo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getNaziv()).isEqualTo("Podsjetnik 1");
        assertThat(found.getOpis()).isEqualTo("Opis podsjetnika");
        assertThat(found.isObavijesten()).isFalse();
        assertThat(found.getStednja().getNaziv()).isEqualTo("Štednja za auto");
        assertThat(found.getStednja().getKorisnik().getIme()).isEqualTo("Test");
    }
}

