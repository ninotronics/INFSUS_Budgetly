package INFSUS.model;

import INFSUS.enums.TrosakEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "trosak")
@AllArgsConstructor
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(name = "transakcijaId")
public class Trosak extends Transakcija {

    @Column(nullable = false, length = 30)
    private TrosakEnum trosakKategorija;
}
