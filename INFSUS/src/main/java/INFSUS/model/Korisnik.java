package INFSUS.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "korisnik")
@AllArgsConstructor
@RequiredArgsConstructor
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Unesi ime pravilno")
    private String ime;

    @NotBlank(message = "Unesi prezime pravilno")
    private String prezime;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String korisnickoIme;

    @NotNull
    private String hashLozinka;

    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal ukupniIznos;

    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal osnovniIznos;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date datumKreiranja;

    @Column(name = "datum_rodenja", nullable = false)
    private LocalDate datumRodenja;

    @Transient
    public int getGodine() {
        return Period.between(this.datumRodenja, LocalDate.now()).getYears();
    }

    // === Odnosi ===

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ulogaId", nullable = false)
    private Uloga uloga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "valutaId", nullable = false)
    private Valuta valuta;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Stednja> stednje;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transakcija> transakcije;
}
