package INFSUS.repository;

import INFSUS.model.Valuta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ValutaRepoTest {
    @Autowired
    private ValutaRepo valutaRepo;

    @Test
    void contextLoads() {
        assertThat(valutaRepo).isNotNull();
    }

    @Test
    void testSaveAndFindById() {
        Valuta valuta = new Valuta();
        valuta.setKod("EUR");
        valuta.setSimbol("€");
        valuta.setNaziv("Euro");
        valuta.setTecajPremaBazi(new java.math.BigDecimal("1.0"));
        Valuta saved = valutaRepo.save(valuta);
        Valuta found = valutaRepo.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getKod()).isEqualTo("EUR");
        assertThat(found.getSimbol()).isEqualTo("€");
        assertThat(found.getNaziv()).isEqualTo("Euro");
        assertThat(found.getTecajPremaBazi()).isEqualByComparingTo(new java.math.BigDecimal("1.0"));
    }
}
