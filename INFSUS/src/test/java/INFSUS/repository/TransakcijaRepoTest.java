package INFSUS.repository;

import INFSUS.model.Transakcija;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransakcijaRepoTest {
    @Autowired
    private TransakcijaRepo transakcijaRepo;

    @Test
    void contextLoads() {
        assertThat(transakcijaRepo).isNotNull();
    }
}

