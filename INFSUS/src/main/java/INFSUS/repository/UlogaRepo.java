package INFSUS.repository;

import INFSUS.model.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UlogaRepo extends JpaRepository<Uloga, Long> {
}

