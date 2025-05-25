package INFSUS.service;

import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.StednjaResponseDTO;

import java.util.List;

public interface StednjaService {
    StednjaResponseDTO createStednja(StednjaRequestDTO request);
    StednjaResponseDTO updateStednja(Long id, StednjaRequestDTO request);
    StednjaResponseDTO getStednja(Long id);
    List<StednjaResponseDTO> getAllStednje();
    void deleteStednja(Long id);
}
