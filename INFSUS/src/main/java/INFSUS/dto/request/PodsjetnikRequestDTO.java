package INFSUS.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodsjetnikRequestDTO {
    private Long stednjaId;
    private String datumPodsjetnika;
    private String naziv;
    private String opis;
}