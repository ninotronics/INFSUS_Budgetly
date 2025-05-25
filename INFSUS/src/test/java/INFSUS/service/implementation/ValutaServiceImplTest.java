package INFSUS.service.implementation;

import INFSUS.dto.response.ValutaResponseDTO;
import INFSUS.model.Valuta;
import INFSUS.repository.ValutaRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ValutaServiceImplTest {
    @Mock
    private ValutaRepo valutaRepo;

    @InjectMocks
    private ValutaServiceImpl valutaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Valuta valuta = new Valuta(1L, "EUR", "€", "Euro", new BigDecimal("7.53450"));
        when(valutaRepo.findAll()).thenReturn(List.of(valuta));
        List<ValutaResponseDTO> result = valutaService.getAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getKod()).isEqualTo("EUR");
    }

    @Test
    void testGetById_found() {
        Valuta valuta = new Valuta(1L, "EUR", "€", "Euro", new BigDecimal("7.53450"));
        when(valutaRepo.findById(1L)).thenReturn(Optional.of(valuta));
        ValutaResponseDTO result = valutaService.getById(1L);
        assertThat(result.getKod()).isEqualTo("EUR");
    }

    @Test
    void testGetById_notFound() {
        when(valutaRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> valutaService.getById(2L));
    }
}

