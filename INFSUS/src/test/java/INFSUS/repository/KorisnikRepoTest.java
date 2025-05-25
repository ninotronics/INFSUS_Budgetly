package INFSUS.repository;

import INFSUS.model.Korisnik;
import INFSUS.model.Uloga;
import INFSUS.model.Valuta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class KorisnikRepoTest {
    @Autowired
    private KorisnikRepo korisnikRepo;
    @Autowired
    private UlogaRepo ulogaRepo;
    @Autowired
    private ValutaRepo valutaRepo;

    @Test
    void contextLoads() {
        assertThat(korisnikRepo).isNotNull();
    }

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
        Korisnik saved = korisnikRepo.save(korisnik);
        Korisnik found = korisnikRepo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getIme()).isEqualTo("Test");
        assertThat(found.getPrezime()).isEqualTo("Korisnik");
        assertThat(found.getEmail()).isEqualTo("test@example.com");
        assertThat(found.getKorisnickoIme()).isEqualTo("testuser");
        assertThat(found.getHashLozinka()).isEqualTo("hash");
        assertThat(found.getUkupniIznos()).isEqualByComparingTo(new java.math.BigDecimal("100.00"));
        assertThat(found.getOsnovniIznos()).isEqualByComparingTo(new java.math.BigDecimal("50.00"));
        assertThat(found.getUloga().getNaziv()).isEqualTo("KORISNIK");
        assertThat(found.getValuta().getKod()).isEqualTo("EUR");
    }
}
