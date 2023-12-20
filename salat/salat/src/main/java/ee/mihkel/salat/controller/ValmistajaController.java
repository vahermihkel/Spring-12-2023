package ee.mihkel.salat.controller;

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

    @GetMapping("lisa-valmistaja")   // localhost:8080/lisa-valmistaja?eesnimi=Mari&perenimi=Laane&vanus=30&riik=Eesti
    public List<Valmistaja> lisaValmistaja(
            @RequestParam String eesnimi, @RequestParam String perenimi,
            @RequestParam int vanus, @RequestParam String riik) {
        Valmistaja valmistaja = new Valmistaja(); // {id: 0, eesnimi: null, perenimi: null, vanus: 0, riik: null}
        valmistaja.setEesnimi(eesnimi);
        valmistaja.setPerenimi(perenimi);
        valmistaja.setVanus(vanus);
        valmistaja.setRiik(riik);
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
