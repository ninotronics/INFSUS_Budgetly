package INFSUS.service.implementation;

import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.mapper.TransakcijaMapper;
import INFSUS.model.Transakcija;
import INFSUS.repository.TransakcijaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransakcijaServiceImplTest {
    @Mock
    private TransakcijaRepo transakcijaRepository;
    @InjectMocks
    private TransakcijaServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTransakcije_success() {
        when(transakcijaRepository.findAll()).thenReturn(List.of(new Transakcija()));
        List<TransakcijaResponseDTO> result = service.getAllTransakcije();
        assertNotNull(result);
    }
}

