package ee.mihkel.salat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter // tekitab igale muutujale get funktsiooni
@Setter // tekitab igale muutujale set funktsiooni
@Entity // teeb temast rakenduse käima pannes andmebaasi tabeli
public class KontaktAndmed {
    @Id // on andmebaasis unikaalne (kordumatu) ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // genereeritakse automaatselt alates 1st
    private Long id;
    private String telefon;
    private String aadress;
    private String email;
}
