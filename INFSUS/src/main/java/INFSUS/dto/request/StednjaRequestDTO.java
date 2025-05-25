package INFSUS.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StednjaRequestDTO {
    private String naziv;
    private String opis;
    private LocalDate datumKraj;
    private BigDecimal ciljniIznos;
    private Long korisnikId;
}
