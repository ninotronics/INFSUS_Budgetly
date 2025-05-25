package INFSUS.mapper;

import INFSUS.dto.response.PodsjetnikResponseDTO;
import INFSUS.model.Podsjetnik;

import java.time.format.DateTimeFormatter;

public class PodsjetnikMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static PodsjetnikResponseDTO toDto(Podsjetnik podsjetnik) {
        return new PodsjetnikResponseDTO(
                podsjetnik.getId(),
                podsjetnik.getNaziv(),
                podsjetnik.getOpis(),
                podsjetnik.isObavijesten(),
                podsjetnik.getDatumPodsjetnika().format(formatter)
        );
    }
}
