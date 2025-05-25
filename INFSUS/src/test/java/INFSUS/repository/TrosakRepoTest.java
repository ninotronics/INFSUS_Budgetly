package INFSUS.repository;

import INFSUS.model.Korisnik;
import INFSUS.model.Trosak;
import INFSUS.model.Uloga;
import INFSUS.model.Valuta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TrosakRepoTest {
    @Autowired
    private TrosakRepo trosakRepo;
    @Autowired
    private KorisnikRepo korisnikRepo;
    @Autowired
    private UlogaRepo ulogaRepo;
    @Autowired
    private ValutaRepo valutaRepo;

    @Test
    void contextLoads() {
        assertThat(trosakRepo).isNotNull();
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
        korisnik = korisnikRepo.save(korisnik);

        // 4. Kreiraj i spremi trosak
        Trosak trosak = new Trosak();
        trosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        trosak.setDatumTransakcije(java.time.LocalDate.now());
        trosak.setOpis("Test opis troska");
        trosak.setIznos(new java.math.BigDecimal("321.00"));
        trosak.setVrsta("Trosak");
        trosak.setKorisnik(korisnik);
        Trosak saved = trosakRepo.save(trosak);
        Trosak found = trosakRepo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTrosakKategorija()).isEqualTo(INFSUS.enums.TrosakEnum.HRANA);
        assertThat(found.getDatumTransakcije()).isEqualTo(trosak.getDatumTransakcije());
        assertThat(found.getOpis()).isEqualTo("Test opis troska");
        assertThat(found.getIznos()).isEqualByComparingTo(new java.math.BigDecimal("321.00"));
        assertThat(found.getVrsta()).isEqualTo("Trosak");
        assertThat(found.getKorisnik().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindByKorisnikId() {
        // Priprema korisnika i povezanih entiteta
        Uloga uloga = new Uloga();
        uloga.setNaziv("KORISNIK");
        uloga = ulogaRepo.save(uloga);
        Valuta valuta = new Valuta();
        valuta.setNaziv("Euro");
        valuta.setKod("EUR");
        valuta.setSimbol("€");
        valuta.setTecajPremaBazi(new java.math.BigDecimal("7.53450"));
        valuta = valutaRepo.save(valuta);
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
        // Dodaj dva troška za korisnika
        Trosak trosak1 = new Trosak();
        trosak1.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        trosak1.setDatumTransakcije(java.time.LocalDate.now());
        trosak1.setOpis("Test trosak 1");
        trosak1.setIznos(new java.math.BigDecimal("10.00"));
        trosak1.setVrsta("Trosak");
        trosak1.setKorisnik(korisnik);
        trosakRepo.save(trosak1);
        Trosak trosak2 = new Trosak();
        trosak2.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        trosak2.setDatumTransakcije(java.time.LocalDate.now());
        trosak2.setOpis("Test trosak 2");
        trosak2.setIznos(new java.math.BigDecimal("20.00"));
        trosak2.setVrsta("Trosak");
        trosak2.setKorisnik(korisnik);
        trosakRepo.save(trosak2);
        // Testiraj findByKorisnikId
        var rezultati = trosakRepo.findByKorisnikId(korisnik.getId());
        assertThat(rezultati).hasSize(2);
        assertThat(rezultati).extracting(Trosak::getOpis).containsExactlyInAnyOrder("Test trosak 1", "Test trosak 2");
    }
}
