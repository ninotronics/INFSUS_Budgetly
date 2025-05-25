package INFSUS.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "podsjetnik")
@AllArgsConstructor
@RequiredArgsConstructor
public class Podsjetnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stednjaId", nullable = false)
    @JsonBackReference
    private Stednja stednja;

    @Column(nullable = false)
    private LocalDateTime datumPodsjetnika;

    @Column(nullable = false, length = 30)
    private String naziv;

    @Column(length = 255)
    private String opis;

    @Column(nullable = false)
    private boolean obavijesten = false;
}