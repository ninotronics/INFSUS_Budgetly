package INFSUS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TrosakResponseDTO {
    private Long id;
    private String opis;
    private BigDecimal iznos;
    private String vrsta;
    private String trosakKategorija;
    private LocalDate datumTransakcije;
    private Long korisnikId;
}