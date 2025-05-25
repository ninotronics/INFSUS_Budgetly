package INFSUS.service;
import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.model.Trosak;

import java.util.List;

public interface TrosakService {
    TrosakResponseDTO createTrosak(TrosakRequestDTO trosak);
    TrosakResponseDTO updateTrosak(Long id, TrosakRequestDTO trosak);
    TrosakResponseDTO getTrosak(Long id);
    List<TrosakResponseDTO> getAllTroskovi();
    void deleteTrosak(Long id);
}