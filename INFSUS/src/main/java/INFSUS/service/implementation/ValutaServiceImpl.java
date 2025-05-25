package INFSUS.service.implementation;

import INFSUS.dto.response.ValutaResponseDTO;
import INFSUS.model.Valuta;
import INFSUS.repository.ValutaRepo;
import INFSUS.service.ValutaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValutaServiceImpl implements ValutaService {

    private final ValutaRepo valutaRepo;

    @Override
    public List<ValutaResponseDTO> getAll() {
        return valutaRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ValutaResponseDTO getById(Long id) {
        Valuta valuta = valutaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Valuta not found"));
        return toDto(valuta);
    }

    private ValutaResponseDTO toDto(Valuta valuta) {
        ValutaResponseDTO dto = new ValutaResponseDTO();
        dto.setId(valuta.getId());
        dto.setKod(valuta.getKod());
        dto.setSimbol(valuta.getSimbol());
        dto.setNaziv(valuta.getNaziv());
        return dto;
    }
}