package INFSUS.mapper;

import INFSUS.dto.request.StednjaRequestDTO;
import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.dto.response.StednjaResponseDTO;
import INFSUS.model.Korisnik;
import INFSUS.model.Stednja;

import java.time.LocalDate;
import java.util.List;


public class StednjaMapper {

    public static Stednja toEntity(StednjaRequestDTO dto, Korisnik korisnik) {
        Stednja s = new Stednja();
        s.setNaziv(dto.getNaziv());
        s.setOpis(dto.getOpis());
        s.setDatumKreiranja(LocalDate.now());
        s.setDatumKraj(dto.getDatumKraj());
        s.setCiljniIznos(dto.getCiljniIznos());
        s.setTrenutniIznos(java.math.BigDecimal.ZERO);
        s.setKorisnik(korisnik);
        return s;
    }

    public static StednjaResponseDTO toDto(Stednja s) {
        List<PodsjetnikResponseDTO> podsjetniciDto = s.getPodsjetnici() != null
                ? s.getPodsjetnici().stream()
                .map(PodsjetnikMapper::toDto)
                .toList()
                : List.of();

        return new StednjaResponseDTO(
                s.getId(),
                s.getNaziv(),
                s.getOpis(),
                s.getDatumKreiranja(),
                s.getDatumKraj(),
                s.getCiljniIznos(),
                s.getTrenutniIznos(),
                s.getKorisnik().getId(),
                podsjetniciDto
        );
    }
}
