package INFSUS.service.implementation;
import INFSUS.dto.request.PodsjetnikRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.mapper.PodsjetnikMapper;
import INFSUS.model.Podsjetnik;
import INFSUS.model.Stednja;
import INFSUS.repository.PodsjetnikRepo;
import INFSUS.repository.StednjaRepo;
import INFSUS.service.PodsjetnikService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PodsjetnikServiceImpl implements PodsjetnikService {

    private final PodsjetnikRepo podsjetnikRepository;
    private final StednjaRepo stednjaRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public PodsjetnikResponseDTO createPodsjetnik(PodsjetnikRequestDTO requestDTO) {
        Stednja stednja = stednjaRepository.findById(requestDTO.getStednjaId())
                .orElseThrow(() -> new EntityNotFoundException("Stednja not found"));

        Podsjetnik podsjetnik = new Podsjetnik();
        podsjetnik.setStednja(stednja);
        podsjetnik.setDatumPodsjetnika(LocalDateTime.parse(requestDTO.getDatumPodsjetnika(), formatter));
        podsjetnik.setNaziv(requestDTO.getNaziv());
        podsjetnik.setOpis(requestDTO.getOpis());
        podsjetnik.setObavijesten(false);

        return PodsjetnikMapper.toDto(podsjetnikRepository.save(podsjetnik));
    }

    @Override
    public List<PodsjetnikResponseDTO> getAll() {
        return podsjetnikRepository.findAll().stream()
                .map(PodsjetnikMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PodsjetnikResponseDTO getById(Long id) {
        Podsjetnik podsjetnik = podsjetnikRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Podsjetnik not found"));
        return PodsjetnikMapper.toDto(podsjetnik);
    }

    @Override
    public void deleteById(Long id) {
        if (!podsjetnikRepository.existsById(id)) {
            throw new EntityNotFoundException("Podsjetnik not found");
        }
        podsjetnikRepository.deleteById(id);
    }

    @Override
    public PodsjetnikResponseDTO update(Long id, PodsjetnikRequestDTO requestDTO) {
        Podsjetnik podsjetnik = podsjetnikRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Podsjetnik not found"));

        if (podsjetnik.isObavijesten()) {
            throw new IllegalStateException("Podsjetnik je već obaviješten i ne može se mijenjati.");
        }

        Stednja stednja = stednjaRepository.findById(requestDTO.getStednjaId())
                .orElseThrow(() -> new EntityNotFoundException("Stednja not found"));

        podsjetnik.setStednja(stednja);
        podsjetnik.setDatumPodsjetnika(LocalDateTime.parse(requestDTO.getDatumPodsjetnika(), formatter));
        podsjetnik.setNaziv(requestDTO.getNaziv());
        podsjetnik.setOpis(requestDTO.getOpis());

        return PodsjetnikMapper.toDto(podsjetnikRepository.save(podsjetnik));
    }
}
