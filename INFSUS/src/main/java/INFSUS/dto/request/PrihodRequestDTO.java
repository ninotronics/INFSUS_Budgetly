package INFSUS.dto.request;

import java.math.BigDecimal;

import INFSUS.enums.PrihodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PrihodRequestDTO {
    private String opis;
    private BigDecimal iznos;
    private String vrsta;
    private Long korisnikId;
    private PrihodEnum prihodKategorija;
}