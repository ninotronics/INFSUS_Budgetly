package INFSUS.service.implementation;
import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.mapper.TransakcijaMapper;
import INFSUS.model.Transakcija;
import INFSUS.repository.TransakcijaRepo;
import INFSUS.service.TransakcijaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransakcijaServiceImpl implements TransakcijaService {

    private final TransakcijaRepo transakcijaRepository;

    @Override
    public List<TransakcijaResponseDTO> getAllTransakcije() {
        List<Transakcija> transakcije = transakcijaRepository.findAll();
        return transakcije.stream()
                .map(TransakcijaMapper::toDto)
                .collect(Collectors.toList());
    }
}