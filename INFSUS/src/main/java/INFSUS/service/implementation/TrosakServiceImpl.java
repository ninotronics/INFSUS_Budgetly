package INFSUS.service.implementation;

import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.mapper.TrosakMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Trosak;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.TrosakRepo;
import INFSUS.service.TrosakService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrosakServiceImpl implements TrosakService {

    private final TrosakRepo trosakRepository;
    private final KorisnikRepo korisnikRepository;

    @Override
    @Transactional
    public TrosakResponseDTO createTrosak(TrosakRequestDTO dto) {
        Korisnik korisnik = korisnikRepository.findById(dto.getKorisnikId()).orElseThrow();
        updateKorisnikBalance(korisnik, dto.getIznos().negate());

        Trosak trosak = TrosakMapper.toEntity(dto, korisnik);
        Trosak saved = trosakRepository.save(trosak);
        return TrosakMapper.toDto(saved);
    }

    @Override
    public TrosakResponseDTO updateTrosak(Long id, TrosakRequestDTO updated) {
        Trosak existing = trosakRepository.findById(id).orElseThrow();
        updateKorisnikBalance(existing.getKorisnik(), existing.getIznos());
        updateKorisnikBalance(existing.getKorisnik(), updated.getIznos().negate());
        existing.setDatumTransakcije(java.time.LocalDate.now());
        existing.setOpis(updated.getOpis());
        existing.setIznos(updated.getIznos());
        existing.setTrosakKategorija(updated.getTrosakKategorija());

        return TrosakMapper.toDto(trosakRepository.save(existing));
    }

    @Override
    public TrosakResponseDTO getTrosak(Long id) {
        return trosakRepository.findById(id)
                .map(TrosakMapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<TrosakResponseDTO> getAllTroskovi() {
        return trosakRepository.findAll()
                .stream()
                .map(TrosakMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTrosak(Long id) {
        Trosak trosak = trosakRepository.findById(id).orElseThrow();
        updateKorisnikBalance(trosak.getKorisnik(), trosak.getIznos());

        trosakRepository.delete(trosak);
    }

    private void updateKorisnikBalance(Korisnik korisnik, BigDecimal delta) {
        korisnik.setUkupniIznos(korisnik.getUkupniIznos().add(delta));
        korisnikRepository.save(korisnik);
    }
}
