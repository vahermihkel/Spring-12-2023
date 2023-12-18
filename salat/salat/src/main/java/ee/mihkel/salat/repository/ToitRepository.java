package ee.mihkel.salat.repository;

import ee.mihkel.salat.entity.Toit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToitRepository extends JpaRepository<Toit, Long> {
    // ainult selliseid funktsioon on võimalik siia teha, mis tagastavad
    // Toit või List<Toit>
//    List<Toit> findAllByToiduainedContaining();
}
