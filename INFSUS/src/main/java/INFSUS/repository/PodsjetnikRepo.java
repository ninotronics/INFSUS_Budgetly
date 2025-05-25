package INFSUS.repository;

import INFSUS.model.Podsjetnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodsjetnikRepo extends JpaRepository<Podsjetnik, Long> {
}
