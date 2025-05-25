package INFSUS.repository;

import INFSUS.model.Trosak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrosakRepo extends JpaRepository<Trosak, Long> {
    List<Trosak> findByKorisnikId(Long korisnikId);
}