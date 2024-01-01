package ee.mihkel.salat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

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
    // vasakpoolne tähendab, et see klass pole jagatav
    @OneToOne(cascade = CascadeType.ALL) // parempoolne One tähendab, et siin pole listi
    // Cascade tähendab, et KontaktAndmete tabeli 1 andmerida tekib koos selle klassiga
    //          ja kustub koos selle klassiga (mul pole seega tema osas repository't vaja teha)
    private KontaktAndmed kontaktAndmed;
    // tagataustal:
//    private String telefon; <-- tekivadki Valmitaja klassile
//    private String aadress; <-- tekivadki Valmitaja klassile
//    private String email; <-- tekivadki Valmitaja klassile

//    @ManyToMany
//    private List<Toit> toidud;
}
