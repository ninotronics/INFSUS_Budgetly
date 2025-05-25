package INFSUS.repository;

import INFSUS.model.Stednja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StednjaRepo extends JpaRepository<Stednja, Long> {
}
