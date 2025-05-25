package INFSUS.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transakcija")
@AllArgsConstructor
@RequiredArgsConstructor
public class Transakcija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate datumTransakcije;

    @Column(length = 255)
    private String opis;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal iznos;

    @Column(nullable = false, length = 30)
    private String vrsta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnikId", nullable = false)
    @JsonBackReference
    private Korisnik korisnik;
}