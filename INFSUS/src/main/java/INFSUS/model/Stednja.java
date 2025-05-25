package INFSUS.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "stednja")
@AllArgsConstructor
@RequiredArgsConstructor
public class Stednja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String naziv;

    @Column(length = 255)
    private String opis;

    @Column(nullable = false)
    private LocalDate datumKreiranja;

    @Column(nullable = false)
    private LocalDate datumKraj;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal ciljniIznos;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal trenutniIznos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korisnikId", nullable = false)
    @JsonBackReference
    private Korisnik korisnik;

    @OneToMany(mappedBy = "stednja", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Podsjetnik> podsjetnici;
}