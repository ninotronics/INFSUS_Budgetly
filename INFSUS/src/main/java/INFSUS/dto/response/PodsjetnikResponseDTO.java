package INFSUS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PodsjetnikResponseDTO {
    private Long id;
    private String naziv;
    private String opis;
    private boolean obavijesten;
    private String datumPodsjetnika;
}