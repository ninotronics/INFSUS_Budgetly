package INFSUS.service.implementation;
import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;
import INFSUS.mapper.PrihodMapper;
import INFSUS.model.Korisnik;
import INFSUS.model.Prihod;
import INFSUS.repository.KorisnikRepo;
import INFSUS.repository.PrihodRepo;
import INFSUS.service.PrihodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrihodServiceImpl implements PrihodService {

    private final PrihodRepo prihodRepository;
    private final KorisnikRepo korisnikRepository;

    @Override
    @Transactional
    public PrihodResponseDTO createPrihod(PrihodRequestDTO dto) {
        Korisnik korisnik = korisnikRepository.findById(dto.getKorisnikId()).orElseThrow();
        updateKorisnikBalance(korisnik, dto.getIznos());

        Prihod prihod = PrihodMapper.toEntity(dto, korisnik);
        Prihod saved = prihodRepository.save(prihod);
        return PrihodMapper.toDto(saved);
    }

    @Override
    public PrihodResponseDTO updatePrihod(Long id, PrihodRequestDTO updated) {
        Prihod existing = prihodRepository.findById(id).orElseThrow();

        existing.setDatumTransakcije(java.time.LocalDate.now());
        existing.setOpis(updated.getOpis());
        updateKorisnikBalance(existing.getKorisnik(), existing.getIznos().negate());
        existing.setIznos(updated.getIznos());
        updateKorisnikBalance(existing.getKorisnik(), updated.getIznos());
        existing.setPrihodKategorija(updated.getPrihodKategorija());

        Prihod saved = prihodRepository.save(existing);
        return PrihodMapper.toDto(saved);
    }

    @Override
    public PrihodResponseDTO getPrihod(Long id) {
        Prihod prihod = prihodRepository.findById(id).orElseThrow();
        return PrihodMapper.toDto(prihod);
    }

    @Override
    public List<PrihodResponseDTO> getAllPrihodi() {
        return prihodRepository.findAll()
                .stream()
                .map(PrihodMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePrihod(Long id) {
        Prihod prihod = prihodRepository.findById(id).orElseThrow();
        updateKorisnikBalance(prihod.getKorisnik(), prihod.getIznos().negate());
        prihodRepository.delete(prihod);
    }

    private void updateKorisnikBalance(Korisnik korisnik, BigDecimal delta) {
        korisnik.setUkupniIznos(korisnik.getUkupniIznos().add(delta));
        korisnikRepository.save(korisnik);
    }
}