package INFSUS.service.implementation;

import INFSUS.dto.request.PodsjetnikRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.mapper.PodsjetnikMapper;
import INFSUS.model.Podsjetnik;
import INFSUS.model.Stednja;
import INFSUS.repository.PodsjetnikRepo;
import INFSUS.repository.StednjaRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PodsjetnikServiceImplTest {
    @Mock
    private PodsjetnikRepo podsjetnikRepository;
    @Mock
    private StednjaRepo stednjaRepository;
    @InjectMocks
    private PodsjetnikServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPodsjetnik_success() {
        PodsjetnikRequestDTO dto = new PodsjetnikRequestDTO();
        dto.setStednjaId(1L);
        dto.setDatumPodsjetnika("2024-06-01 12:00");
        dto.setNaziv("Test");
        dto.setOpis("Opis");
        Stednja stednja = new Stednja();
        when(stednjaRepository.findById(1L)).thenReturn(Optional.of(stednja));
        Podsjetnik savedPodsjetnik = new Podsjetnik();
        savedPodsjetnik.setDatumPodsjetnika(LocalDateTime.now()); // Fix: set non-null datumPodsjetnika
        when(podsjetnikRepository.save(any())).thenReturn(savedPodsjetnik);
        PodsjetnikResponseDTO response = service.createPodsjetnik(dto);
        assertNotNull(response);
    }

    @Test
    void createPodsjetnik_stednjaNotFound() {
        PodsjetnikRequestDTO dto = new PodsjetnikRequestDTO();
        dto.setStednjaId(1L);
        when(stednjaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.createPodsjetnik(dto));
    }

    @Test
    void getAll_success() {
        Podsjetnik podsjetnik = new Podsjetnik();
        podsjetnik.setDatumPodsjetnika(LocalDateTime.now()); // Fix: set non-null datumPodsjetnika
        when(podsjetnikRepository.findAll()).thenReturn(List.of(podsjetnik));
        List<PodsjetnikResponseDTO> result = service.getAll();
        assertNotNull(result);
    }

    @Test
    void getById_success() {
        Podsjetnik podsjetnik = new Podsjetnik();
        podsjetnik.setDatumPodsjetnika(LocalDateTime.now()); // Fix: set non-null datumPodsjetnika
        when(podsjetnikRepository.findById(1L)).thenReturn(Optional.of(podsjetnik));
        PodsjetnikResponseDTO result = service.getById(1L);
        assertNotNull(result);
    }

    @Test
    void getById_notFound() {
        when(podsjetnikRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void deleteById_success() {
        when(podsjetnikRepository.existsById(1L)).thenReturn(true);
        doNothing().when(podsjetnikRepository).deleteById(1L);
        assertDoesNotThrow(() -> service.deleteById(1L));
    }

    @Test
    void deleteById_notFound() {
        when(podsjetnikRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteById(1L));
    }

    @Test
    void updatePodsjetnik_success() {
        Long id = 1L;
        PodsjetnikRequestDTO dto = new PodsjetnikRequestDTO();
        dto.setStednjaId(1L);
        dto.setDatumPodsjetnika("2024-06-01 12:00");
        dto.setNaziv("UpdateTest");
        dto.setOpis("UpdateOpis");
        Stednja stednja = new Stednja();
        Podsjetnik podsjetnik = new Podsjetnik();
        podsjetnik.setDatumPodsjetnika(LocalDateTime.now());
        when(stednjaRepository.findById(1L)).thenReturn(Optional.of(stednja));
        when(podsjetnikRepository.findById(id)).thenReturn(Optional.of(podsjetnik));
        when(podsjetnikRepository.save(any())).thenReturn(podsjetnik);
        PodsjetnikResponseDTO response = service.update(id, dto);
        assertNotNull(response);
    }
}
