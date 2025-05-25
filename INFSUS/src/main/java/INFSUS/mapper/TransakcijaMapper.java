package INFSUS.mapper;
import INFSUS.dto.response.TransakcijaResponseDTO;
import INFSUS.model.Transakcija;

public class TransakcijaMapper {
    public static TransakcijaResponseDTO toDto(Transakcija transakcija) {
        return new TransakcijaResponseDTO(
                transakcija.getId(),
                transakcija.getDatumTransakcije(),
                transakcija.getOpis(),
                transakcija.getIznos(),
                transakcija.getVrsta()
        );
    }
}
