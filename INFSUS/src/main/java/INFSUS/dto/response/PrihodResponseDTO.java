package INFSUS.dto.response;
import INFSUS.enums.PrihodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PrihodResponseDTO {
    private Long id;
    private String opis;
    private BigDecimal iznos;
    private String vrsta;
    private PrihodEnum prihodKategorija;
    private LocalDate datumTransakcije;
    private Long korisnikId;
}