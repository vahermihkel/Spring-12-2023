package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.KontaktAndmed;
import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.entity.Toit;
import ee.mihkel.salat.entity.Valmistaja;
import ee.mihkel.salat.repository.ToitRepository;
import ee.mihkel.salat.repository.ValmistajaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ValmistajaController {

    private final ValmistajaRepository valmistajaRepository;
    private final ToitRepository toitRepository;

    public ValmistajaController(ValmistajaRepository valmistajaRepository,
                                ToitRepository toitRepository) {
        this.valmistajaRepository = valmistajaRepository;
        this.toitRepository = toitRepository;
    }

    // localhost:8080/lisa-valmistaja?eesnimi=Mari&perenimi=Laane&vanus=30&riik=Eesti?tel=555&aad=Tammsaare&email=m@m.com
    @GetMapping("lisa-valmistaja")
    public List<Valmistaja> lisaValmistaja(
            @RequestParam String eesnimi, @RequestParam String perenimi,
            @RequestParam int vanus, @RequestParam String riik,
            @RequestParam String tel, @RequestParam String aad, @RequestParam String email) {
        Valmistaja valmistaja = new Valmistaja(); // {id: 0, eesnimi: null, perenimi: null, vanus: 0, riik: null}
        valmistaja.setEesnimi(eesnimi);
        valmistaja.setPerenimi(perenimi);
        valmistaja.setVanus(vanus);
        valmistaja.setRiik(riik);

        KontaktAndmed kontaktAndmed = new KontaktAndmed(); // {id: 0, telefon: null, aadress: null, email: null}
        kontaktAndmed.setTelefon(tel); // {id: 0, telefon: "555", aadress: null, email: null}
        kontaktAndmed.setAadress(aad); // {id: 0, telefon: "555", aadress: "Tammsaare", email: null}
        kontaktAndmed.setEmail(email); // {id: 0, telefon: "555", aadress: "Tammsaare", email: "m@m.com"}
        valmistaja.setKontaktAndmed(kontaktAndmed);
        // ma ei pea panema kontaktRepository.save(), et salvestada kontaktandmeid eraldi real andmebaasi, VAID
        // kui Valmistaja liigub andmebaasi ja tema küljes on setteriga pandud KontaktAndmed, siis ta liigub automaatselt
        // TÄNU cascadeType.All reale @OneToOne sees

        // ja ei saa eraldi kustutada KontaktAndmete rida andmebaasist VAID kui valmistaja kustub, siis kustub
        // temaga seotud KontaktAndmed AUTOMAATSELT
        valmistajaRepository.save(valmistaja);
        return valmistajaRepository.findAll();
    }

    @GetMapping("valmistajad")   // localhost:8080/valmistajad
    public List<Valmistaja> saaValmistajad() {
        return valmistajaRepository.findAll();
    }

    @GetMapping("valmistaja-toidud") // localhost:8080/valmistaja-toidud?id=1
    public List<Toit> valmistajaToidud(@RequestParam Long id) {
        List<Toit> koikToidud = toitRepository.findAll();
        List<Toit> valmistajaToidud = new ArrayList<>();
        for (Toit toit : koikToidud) {
            if (toit.getValmistaja().getId().equals(id)) {
                valmistajaToidud.add(toit);
            }
        }
        return valmistajaToidud;
    }
}
