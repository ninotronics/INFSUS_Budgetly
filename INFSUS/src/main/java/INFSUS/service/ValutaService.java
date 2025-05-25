package INFSUS.service;

import INFSUS.dto.response.ValutaResponseDTO;
import java.util.List;

public interface ValutaService {
    List<ValutaResponseDTO> getAll();
    ValutaResponseDTO getById(Long id);
}