package INFSUS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TransakcijaResponseDTO {
    private Long id;
    private LocalDate datumTransakcije;
    private String opis;
    private BigDecimal iznos;
    private String vrsta;
}

