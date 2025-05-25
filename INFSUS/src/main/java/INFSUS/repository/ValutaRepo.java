package INFSUS.repository;

import INFSUS.model.Valuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValutaRepo extends JpaRepository<Valuta, Long> {
}