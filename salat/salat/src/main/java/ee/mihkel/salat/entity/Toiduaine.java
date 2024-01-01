package ee.mihkel.salat.entity;
// entity, model
// Andmebaasimudelite jaoks
// Nimistu objektidest

// DTO - näitan front-endile teistmoodi kui andmebaasis (teeme tulevikus)

import jakarta.persistence.Entity; // javax - vanasti
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Toiduaine toiduaine = new Toiduaine();
@AllArgsConstructor // Toiduaine toiduaine = new Toiduaine("", 1,2,3);
@Entity
public class Toiduaine {
    @Id
    private String nimi;
    private int rasv;
    private int sysivesik;
    private int valk;

//    @ManyToMany
//    private List<Toit> toidud;
}
