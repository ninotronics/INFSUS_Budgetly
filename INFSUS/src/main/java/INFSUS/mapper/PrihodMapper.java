package INFSUS.mapper;

import INFSUS.dto.request.PrihodRequestDTO;
import INFSUS.dto.response.PrihodResponseDTO;
import INFSUS.model.Korisnik;
import INFSUS.model.Prihod;

import java.time.LocalDate;

public class PrihodMapper {

    public static Prihod toEntity(PrihodRequestDTO dto, Korisnik korisnik) {
        Prihod prihod = new Prihod();
        prihod.setOpis(dto.getOpis());
        prihod.setIznos(dto.getIznos());
        prihod.setVrsta(dto.getVrsta());
        prihod.setPrihodKategorija(dto.getPrihodKategorija());
        prihod.setDatumTransakcije(LocalDate.now());
        prihod.setKorisnik(korisnik);
        return prihod;
    }

    public static PrihodResponseDTO toDto(Prihod prihod) {
        return new PrihodResponseDTO(
                prihod.getId(),
                prihod.getOpis(),
                prihod.getIznos(),
                prihod.getVrsta(),
                prihod.getPrihodKategorija(),
                prihod.getDatumTransakcije(),
                prihod.getKorisnik().getId()
        );
    }
}

