package ee.mihkel.salat.repository;

import ee.mihkel.salat.entity.Kodutoo1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//   @Entity  Selle entity ID tüüp
public interface Kodutoo1Repository extends JpaRepository<Kodutoo1, Long> {
    //                  .save(Kodutoo1 kodutoo1)
    // List<Kodutoo1>   .findAll()
    //                  .deleteById(Long id)

    List<Kodutoo1> findAllByNumberGreaterThan(int nr);
}
