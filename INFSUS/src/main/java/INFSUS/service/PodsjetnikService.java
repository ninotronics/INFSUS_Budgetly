package INFSUS.service;

import INFSUS.dto.request.PodsjetnikRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;

import java.util.List;

public interface PodsjetnikService {
    PodsjetnikResponseDTO createPodsjetnik(PodsjetnikRequestDTO requestDTO);
    List<PodsjetnikResponseDTO> getAll();
    PodsjetnikResponseDTO getById(Long id);
    void deleteById(Long id);
    PodsjetnikResponseDTO update(Long id, PodsjetnikRequestDTO requestDTO);
}