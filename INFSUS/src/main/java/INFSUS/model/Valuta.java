package INFSUS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "valuta")
@AllArgsConstructor
@RequiredArgsConstructor
public class Valuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    private String kod;

    @Column(nullable = false, length = 5)
    private String simbol;

    @Column(nullable = false, length = 30)
    private String naziv;

    @Column(nullable = false, precision = 12, scale = 6)
    private BigDecimal tecajPremaBazi;
}