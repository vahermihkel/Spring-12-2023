package ee.mihkel.salat.repository;

import ee.mihkel.salat.entity.Toiduaine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToiduaineRepository extends JpaRepository<Toiduaine, String> {
    List<Toiduaine> findAllByValkBetween(int min, int max);
}
