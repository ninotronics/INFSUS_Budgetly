package INFSUS.service.implementation;

import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;
import INFSUS.mapper.PrihodMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Prihod;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.PrihodRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PrihodServiceImplTest {
    @Mock
    private PrihodRepo prihodRepository;
    @Mock
    private KorisnikRepo korisnikRepository;
    @InjectMocks
    private PrihodServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPrihod_success() {
        PrihodRequestDTO dto = new PrihodRequestDTO();
        dto.setKorisnikId(1L);
        dto.setIznos(BigDecimal.TEN);
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.ZERO);
        korisnik.setId(1L); // Fix: set id
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik));
        Prihod savedPrihod = new Prihod();
        savedPrihod.setKorisnik(korisnik); // Fix: set korisnik
        savedPrihod.setIznos(BigDecimal.TEN);
        when(prihodRepository.save(any())).thenReturn(savedPrihod);
        PrihodResponseDTO response = service.createPrihod(dto);
        assertNotNull(response);
    }

    @Test
    void createPrihod_korisnikNotFound() {
        PrihodRequestDTO dto = new PrihodRequestDTO();
        dto.setKorisnikId(1L);
        when(korisnikRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.createPrihod(dto));
    }

    @Test
    void updatePrihod_success() {
        PrihodRequestDTO dto = new PrihodRequestDTO();
        dto.setIznos(BigDecimal.TEN);
        Prihod prihod = new Prihod();
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.ZERO); // Fix: set ukupniIznos
        korisnik.setId(1L); // Fix: set id
        prihod.setKorisnik(korisnik);
        prihod.setIznos(BigDecimal.ONE);
        when(prihodRepository.findById(1L)).thenReturn(Optional.of(prihod));
        when(prihodRepository.save(any())).thenReturn(prihod);
        PrihodResponseDTO response = service.updatePrihod(1L, dto);
        assertNotNull(response);
    }

    @Test
    void getPrihod_success() {
        Prihod prihod = new Prihod();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        prihod.setKorisnik(korisnik); // Fix: set korisnik
        when(prihodRepository.findById(1L)).thenReturn(Optional.of(prihod));
        PrihodResponseDTO response = service.getPrihod(1L);
        assertNotNull(response);
    }

    @Test
    void getPrihod_notFound() {
        when(prihodRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.getPrihod(1L));
    }

    @Test
    void getAllPrihodi_success() {
        Prihod prihod = new Prihod();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        prihod.setKorisnik(korisnik); // Fix: set korisnik
        when(prihodRepository.findAll()).thenReturn(List.of(prihod));
        List<PrihodResponseDTO> result = service.getAllPrihodi();
        assertNotNull(result);
    }

    @Test
    void deletePrihod_success() {
        Prihod prihod = new Prihod();
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.ZERO); // Fix: set ukupniIznos
        korisnik.setId(1L); // Fix: set id
        prihod.setKorisnik(korisnik);
        prihod.setIznos(BigDecimal.ONE);
        when(prihodRepository.findById(1L)).thenReturn(Optional.of(prihod));
        doNothing().when(prihodRepository).delete(prihod);
        when(korisnikRepository.save(any())).thenReturn(korisnik); // Fix: mock save
        assertDoesNotThrow(() -> service.deletePrihod(1L));
    }

    @Test
    void deletePrihod_notFound() {
        when(prihodRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.deletePrihod(1L));
    }
}

