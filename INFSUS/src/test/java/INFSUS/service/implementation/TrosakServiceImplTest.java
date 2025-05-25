package INFSUS.service.implementation;

import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.mapper.TrosakMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Trosak;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.TrosakRepo;
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

class TrosakServiceImplTest {
    @Mock
    private TrosakRepo trosakRepository;
    @Mock
    private KorisnikRepo korisnikRepository;
    @InjectMocks
    private TrosakServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrosak_success() {
        TrosakRequestDTO dto = new TrosakRequestDTO();
        dto.setKorisnikId(1L);
        dto.setIznos(BigDecimal.TEN);
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.valueOf(100));
        korisnik.setId(1L); // Fix: set id
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik));
        Trosak savedTrosak = new Trosak();
        savedTrosak.setKorisnik(korisnik);
        savedTrosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        when(trosakRepository.save(any())).thenReturn(savedTrosak);
        TrosakResponseDTO response = service.createTrosak(dto);
        assertNotNull(response);
    }

    @Test
    void createTrosak_korisnikNotFound() {
        TrosakRequestDTO dto = new TrosakRequestDTO();
        dto.setKorisnikId(1L);
        when(korisnikRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.createTrosak(dto));
    }

    @Test
    void updateTrosak_success() {
        TrosakRequestDTO dto = new TrosakRequestDTO();
        dto.setIznos(BigDecimal.TEN);
        dto.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        Trosak trosak = new Trosak();
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.valueOf(100));
        korisnik.setId(1L);
        trosak.setKorisnik(korisnik);
        trosak.setIznos(BigDecimal.ONE);
        trosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        when(trosakRepository.findById(1L)).thenReturn(Optional.of(trosak));
        when(trosakRepository.save(any())).thenAnswer(invocation -> {
            Trosak arg = invocation.getArgument(0);
            if (arg.getTrosakKategorija() == null) {
                arg.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
            }
            return arg;
        });
        TrosakResponseDTO response = service.updateTrosak(1L, dto);
        assertNotNull(response);
    }

    @Test
    void getTrosak_success() {
        Trosak trosak = new Trosak();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        trosak.setKorisnik(korisnik); // Fix: set korisnik
        trosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        when(trosakRepository.findById(1L)).thenReturn(Optional.of(trosak));
        TrosakResponseDTO response = service.getTrosak(1L);
        assertNotNull(response);
    }

    @Test
    void getTrosak_notFound() {
        when(trosakRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.getTrosak(1L));
    }

    @Test
    void getAllTroskovi_success() {
        Trosak trosak = new Trosak();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L);
        trosak.setKorisnik(korisnik);
        trosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        when(trosakRepository.findAll()).thenReturn(List.of(trosak));
        List<TrosakResponseDTO> result = service.getAllTroskovi();
        assertNotNull(result);
    }

    @Test
    void deleteTrosak_success() {
        Trosak trosak = new Trosak();
        Korisnik korisnik = new Korisnik();
        korisnik.setUkupniIznos(BigDecimal.valueOf(100));
        korisnik.setId(1L);
        trosak.setKorisnik(korisnik);
        trosak.setIznos(BigDecimal.ONE);
        trosak.setTrosakKategorija(INFSUS.enums.TrosakEnum.HRANA);
        when(trosakRepository.findById(1L)).thenReturn(Optional.of(trosak));
        doNothing().when(trosakRepository).delete(trosak);
        when(korisnikRepository.save(any())).thenReturn(korisnik);
        assertDoesNotThrow(() -> service.deleteTrosak(1L));
    }

    @Test
    void deleteTrosak_notFound() {
        when(trosakRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.deleteTrosak(1L));
    }
}

