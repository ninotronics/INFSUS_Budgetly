package INFSUS.repository;
import INFSUS.model.Transakcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransakcijaRepo extends JpaRepository<Transakcija, Long> {

}
