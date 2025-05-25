package INFSUS.service.implementation;

import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.StednjaResponseDTO;
import INFSUS.mapper.StednjaMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Stednja;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.StednjaRepo;
import INFSUS.service.StednjaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StednjaServiceImpl implements StednjaService {

    private final StednjaRepo stednjaRepository;
    private final KorisnikRepo korisnikRepository;

    @Override
    @Transactional
    public StednjaResponseDTO createStednja(StednjaRequestDTO request) {
        Korisnik korisnik = korisnikRepository.findById(request.getKorisnikId()).orElseThrow();
        Stednja stednja = StednjaMapper.toEntity(request, korisnik);
        return StednjaMapper.toDto(stednjaRepository.save(stednja));
    }

    @Override
    @Transactional
    public StednjaResponseDTO updateStednja(Long id, StednjaRequestDTO request) {
        Stednja existing = stednjaRepository.findById(id).orElseThrow();
        existing.setNaziv(request.getNaziv());
        existing.setOpis(request.getOpis());
        existing.setDatumKraj(request.getDatumKraj());
        existing.setCiljniIznos(request.getCiljniIznos());
        return StednjaMapper.toDto(stednjaRepository.save(existing));
    }

    @Override
    public StednjaResponseDTO getStednja(Long id) {
        return stednjaRepository.findById(id)
                .map(StednjaMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<StednjaResponseDTO> getAllStednje() {
        return stednjaRepository.findAll().stream()
                .map(StednjaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStednja(Long id) {
        stednjaRepository.deleteById(id);
    }
}