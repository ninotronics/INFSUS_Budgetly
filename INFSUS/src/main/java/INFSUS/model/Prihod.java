package INFSUS.model;


import INFSUS.enums.PrihodEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "prihod")
@AllArgsConstructor
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(name = "transakcijaId")
public class Prihod extends Transakcija {

    @Column(nullable = false, length = 30)
    private PrihodEnum prihodKategorija;
}
