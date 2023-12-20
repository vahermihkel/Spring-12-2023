package ee.mihkel.salat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name = "valmistaja") --> seda ta teebki andmebaasis
public class Valmistaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eesnimi;
    private String perenimi;
    private int vanus;
    private String riik;
}
