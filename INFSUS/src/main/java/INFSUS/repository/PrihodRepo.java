package INFSUS.repository;

import INFSUS.model.Prihod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrihodRepo extends JpaRepository<Prihod, Long> {
    List<Prihod> findByKorisnikId(Long korisnikId);
}