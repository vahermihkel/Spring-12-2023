package ee.mihkel.salat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Toit {
    @Id    // Auto = Postgres-st seadistus,
    // UUID = juhuslikult genereeritud tähed ja numbrid: 190e02e5-e768-4b11-b346-ef77e4224003
        // Sequence = Saan öelda ise mitme kaupa ta suureneb (100 kaupa või 50 kaupa ja
        //          saan ise öelda, mitmendast IDst alusta
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity = 1,2,3,4...
    private Long id; // 3
    private String nimetus; // Kartulisalat
    @ManyToMany
    private List<Toiduaine> toiduained; // {nimi: Kartul, valk: 2}, {nimi: Vorst, valk: 8}

    @ManyToOne
//    @JoinColumn(name = "valmistaja_id") --> niimoodi ta seobki by default
    //  name = "tabel_primaarvõti"     --> ei pea panema, see on rõhutamiseks
    private Valmistaja valmistaja;

    // @ManyToOne
    // private ÜKS KLASS muutuja

    // @ManyToMany
    // private List<KLASS> muutuja
}
