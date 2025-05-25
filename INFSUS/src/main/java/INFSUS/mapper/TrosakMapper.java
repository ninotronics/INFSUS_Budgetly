package INFSUS.mapper;

import INFSUS.dto.request.TrosakRequestDTO;
import INFSUS.dto.response.TrosakResponseDTO;
import INFSUS.model.Korisnik;
import INFSUS.model.Trosak;

import java.time.LocalDate;

public class TrosakMapper {

    public static Trosak toEntity(TrosakRequestDTO dto, Korisnik korisnik) {
        Trosak trosak = new Trosak();
        trosak.setOpis(dto.getOpis());
        trosak.setIznos(dto.getIznos());
        trosak.setVrsta(dto.getVrsta());
        trosak.setTrosakKategorija(dto.getTrosakKategorija());
        trosak.setDatumTransakcije(LocalDate.now());
        trosak.setKorisnik(korisnik);
        return trosak;
    }

    public static TrosakResponseDTO toDto(Trosak trosak) {
        return new TrosakResponseDTO(
                trosak.getId(),
                trosak.getOpis(),
                trosak.getIznos(),
                trosak.getVrsta(),
                trosak.getTrosakKategorija().name(),
                trosak.getDatumTransakcije(),
                trosak.getKorisnik().getId()
        );
    }
}
