package INFSUS.service;

import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.model.Transakcija;

import java.util.List;

public interface TransakcijaService {
    List<TransakcijaResponseDTO> getAllTransakcije();
}