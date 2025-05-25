package INFSUS.service.implementation;

import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.StednjaResponseDTO;
import INFSUS.mapper.StednjaMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Stednja;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.StednjaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StednjaServiceImplTest {
    @Mock
    private StednjaRepo stednjaRepository;
    @Mock
    private KorisnikRepo korisnikRepository;
    @InjectMocks
    private StednjaServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStednja_success() {
        StednjaRequestDTO dto = new StednjaRequestDTO();
        dto.setKorisnikId(1L);
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik));
        Stednja savedStednja = new Stednja();
        savedStednja.setKorisnik(korisnik); // Fix: set korisnik
        when(stednjaRepository.save(any())).thenReturn(savedStednja);
        StednjaResponseDTO response = service.createStednja(dto);
        assertNotNull(response);
    }

    @Test
    void createStednja_korisnikNotFound() {
        StednjaRequestDTO dto = new StednjaRequestDTO();
        dto.setKorisnikId(1L);
        when(korisnikRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.createStednja(dto));
    }

    @Test
    void updateStednja_success() {
        StednjaRequestDTO dto = new StednjaRequestDTO();
        Stednja stednja = new Stednja();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        stednja.setKorisnik(korisnik); // Fix: set korisnik
        when(stednjaRepository.findById(1L)).thenReturn(Optional.of(stednja));
        when(stednjaRepository.save(any())).thenReturn(stednja);
        StednjaResponseDTO response = service.updateStednja(1L, dto);
        assertNotNull(response);
    }

    @Test
    void getStednja_success() {
        Stednja stednja = new Stednja();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        stednja.setKorisnik(korisnik); // Fix: set korisnik
        when(stednjaRepository.findById(1L)).thenReturn(Optional.of(stednja));
        StednjaResponseDTO response = service.getStednja(1L);
        assertNotNull(response);
    }

    @Test
    void getStednja_notFound() {
        when(stednjaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.getStednja(1L));
    }

    @Test
    void getAllStednje_success() {
        Stednja stednja = new Stednja();
        Korisnik korisnik = new Korisnik();
        korisnik.setId(1L); // Fix: set id
        stednja.setKorisnik(korisnik); // Fix: set korisnik
        when(stednjaRepository.findAll()).thenReturn(List.of(stednja));
        List<StednjaResponseDTO> result = service.getAllStednje();
        assertNotNull(result);
    }

    @Test
    void deleteStednja_success() {
        doNothing().when(stednjaRepository).deleteById(1L);
        assertDoesNotThrow(() -> service.deleteStednja(1L));
    }
}

