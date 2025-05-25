package INFSUS.service.implementation;

import INFSUS.dto.request.KorisnikPromjenaValuteDTO;
import INFSUS.model.Korisnik;
import INFSUS.model.Valuta;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.ValutaRepo;
import INFSUS.repository.UlogaRepo;
import INFSUS.repository.PrihodRepo;
import INFSUS.repository.TrosakRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KorisnikServiceImplTest {
    @Mock
    private KorisnikRepo korisnikRepo;
    @Mock
    private ValutaRepo valutaRepo;
    @Mock
    private UlogaRepo ulogaRepo;
    @Mock
    private PrihodRepo prihodRepo;
    @Mock
    private TrosakRepo trosakRepo;
    @InjectMocks
    private KorisnikServiceImpl korisnikService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPromijeniValutu() {
        // priprema podataka
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        korisnik.setUkupniIznos(new BigDecimal("100.00"));
        korisnik.setOsnovniIznos(new BigDecimal("50.00"));
        Valuta staraValuta = new Valuta();
        staraValuta.setId(1L);
        staraValuta.setTecajPremaBazi(new BigDecimal("7.5"));
        korisnik.setValuta(staraValuta);
        Valuta novaValuta = new Valuta();
        novaValuta.setId(2L);
        novaValuta.setTecajPremaBazi(new BigDecimal("1.0"));
        KorisnikPromjenaValuteDTO dto = new KorisnikPromjenaValuteDTO();
        dto.setValutaId(2L);
        when(korisnikRepo.findById(1L)).thenReturn(Optional.of(korisnik));
        when(valutaRepo.findById(2L)).thenReturn(Optional.of(novaValuta));
        when(prihodRepo.findAll()).thenReturn(java.util.Collections.emptyList());
        when(trosakRepo.findAll()).thenReturn(java.util.Collections.emptyList());
        korisnikService.promijeniValutu(1L, dto);
        verify(korisnikRepo).save(any(Korisnik.class));
    }
}
