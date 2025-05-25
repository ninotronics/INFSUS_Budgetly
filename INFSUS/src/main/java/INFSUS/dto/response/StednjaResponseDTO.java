package INFSUS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StednjaResponseDTO {
    private Long id;
    private String naziv;
    private String opis;
    private LocalDate datumKreiranja;
    private LocalDate datumKraj;
    private BigDecimal ciljniIznos;
    private BigDecimal trenutniIznos;
    private Long korisnik;
    private List<PodsjetnikResponseDTO> podsjetnici;
}