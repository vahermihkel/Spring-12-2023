package ee.mihkel.salat.repository;

import ee.mihkel.salat.entity.Valmistaja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValmistajaRepository extends JpaRepository<Valmistaja, Long> {
}
