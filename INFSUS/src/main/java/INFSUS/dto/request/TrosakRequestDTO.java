package INFSUS.dto.request;

import java.math.BigDecimal;

import INFSUS.enums.TrosakEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TrosakRequestDTO {
    private String opis;
    private BigDecimal iznos;
    private String vrsta;
    private Long korisnikId;
    private TrosakEnum trosakKategorija;
}