package INFSUS.service;

import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;
import INFSUS.model.Prihod;

import java.util.List;

public interface PrihodService {
    PrihodResponseDTO createPrihod(PrihodRequestDTO prihod);
    PrihodResponseDTO updatePrihod(Long id, PrihodRequestDTO prihod);
    PrihodResponseDTO getPrihod(Long id);
    List<PrihodResponseDTO> getAllPrihodi();
    void deletePrihod(Long id);
}