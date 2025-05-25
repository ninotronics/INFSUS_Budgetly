package INFSUS.repository;

import INFSUS.enums.PrihodEnum;
import INFSUS.model.Korisnik;
import INFSUS.model.Prihod;
import INFSUS.model.Uloga;
import INFSUS.model.Valuta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PrihodRepoTest {
    @Autowired
    private PrihodRepo prihodRepo;
    @Autowired
    private KorisnikRepo korisnikRepo;
    @Autowired
    private UlogaRepo ulogaRepo;
    @Autowired
    private ValutaRepo valutaRepo;

    @Test
    void contextLoads() {
        assertThat(prihodRepo).isNotNull();
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

        // 4. Kreiraj i spremi prihod
        Prihod prihod = new Prihod();
        prihod.setPrihodKategorija(PrihodEnum.PLAĆA);
        prihod.setDatumTransakcije(java.time.LocalDate.now());
        prihod.setOpis("Test opis prihoda");
        prihod.setIznos(new java.math.BigDecimal("123.45"));
        prihod.setVrsta("Prihod");
        prihod.setKorisnik(korisnik);
        Prihod saved = prihodRepo.save(prihod);
        Prihod found = prihodRepo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getPrihodKategorija()).isEqualTo(PrihodEnum.PLAĆA);
        assertThat(found.getDatumTransakcije()).isEqualTo(prihod.getDatumTransakcije());
        assertThat(found.getOpis()).isEqualTo("Test opis prihoda");
        assertThat(found.getIznos()).isEqualByComparingTo(new java.math.BigDecimal("123.45"));
        assertThat(found.getVrsta()).isEqualTo("Prihod");
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
        // Dodaj dva prihoda za korisnika
        Prihod prihod1 = new Prihod();
        prihod1.setPrihodKategorija(PrihodEnum.PLAĆA);
        prihod1.setDatumTransakcije(java.time.LocalDate.now());
        prihod1.setOpis("Test prihod 1");
        prihod1.setIznos(new java.math.BigDecimal("100.00"));
        prihod1.setVrsta("Prihod");
        prihod1.setKorisnik(korisnik);
        prihodRepo.save(prihod1);
        Prihod prihod2 = new Prihod();
        prihod2.setPrihodKategorija(PrihodEnum.PLAĆA);
        prihod2.setDatumTransakcije(java.time.LocalDate.now());
        prihod2.setOpis("Test prihod 2");
        prihod2.setIznos(new java.math.BigDecimal("200.00"));
        prihod2.setVrsta("Prihod");
        prihod2.setKorisnik(korisnik);
        prihodRepo.save(prihod2);
        // Testiraj findByKorisnikId
        var rezultati = prihodRepo.findByKorisnikId(korisnik.getId());
        assertThat(rezultati).hasSize(2);
        assertThat(rezultati).extracting(Prihod::getOpis).containsExactlyInAnyOrder("Test prihod 1", "Test prihod 2");
    }
}
