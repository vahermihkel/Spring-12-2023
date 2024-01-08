package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.entity.Toit;
import ee.mihkel.salat.entity.Valmistaja;
import ee.mihkel.salat.repository.ToiduaineRepository;
import ee.mihkel.salat.repository.ToitRepository;
import ee.mihkel.salat.repository.ValmistajaRepository;
import ee.mihkel.salat.service.ToiduaineService;
import ee.mihkel.salat.util.ToiduaineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000") // takistab CORS errorit (ei lasta teisi rakendusi meie backendile ligi)
public class ToitController {

    @Autowired
    ToitRepository toitRepository;
    @Autowired
    ToiduaineRepository toiduaineRepository;

    @Autowired
    ValmistajaRepository valmistajaRepository;

    @Autowired
    ToiduaineService toiduaineService;

    @GetMapping("koik-toidud") // localhost:8080/koik-toidud
    public ResponseEntity<List<Toit>> koikToidud() {
       return ResponseEntity.ok(toitRepository.findAll());
    }

    @GetMapping("lisa-toit") // localhost:8080/lisa-toit?nimetus=Kartulisalat&toiduainedIds=kartul,vorst,hapukoor&valmistajaId=3
    public ResponseEntity<List<Toit>> lisaToit(
            @RequestParam String nimetus,
            @RequestParam List<String> toiduainedIds,
//            @RequestParam String[] toiduainedIds
            @RequestParam Long valmistajaId // 5
            ) {

        Toit toit = new Toit(); // {id: 0, nimetus: "", toiduained: [], valmistaja: null}
        //toit.setId(id);        //  {id: 0, nimetus: "", toiduained: [], valmistaja: null}
        toit.setNimetus(nimetus); //{id: 0, nimetus: "kartulisalat", toiduained: [], valmistaja: null}

        Valmistaja valmistaja = valmistajaRepository.findById(valmistajaId).get(); // Võtan selle ID (5) ---> võtan andmebaasist terve rea
        toit.setValmistaja(valmistaja); //{id: 1, nimetus: "kartulisalat", toiduained: [], valmistaja: {TERVE RIDA ANDMEBAASIST}}

        List<Toiduaine> toitToiduained = toiduaineService.saaToiduained(toiduainedIds);
        toit.setToiduained(toitToiduained);

        toitRepository.save(toit);
        return ResponseEntity.status(201).body(toitRepository.findAll());
    }

    // 1xx - informatsiooniline
    // 200 - edukas päring
    // 201 - edukalt lisatud
    // 2xx - edukad
    // 3xx - ümbersuunamised
    // 4xx - kliendipoolsed vead (päringu väljakutsuja tegi midagi valesti)
    // 404 - API otspunkt URL vale
    // 400 - üldine
    // 401/403 - autentimisega seotud
    // 405 - tüübi viga PUT, POST, DELETE on vale
    // 415 - body viga ehk annan body valel kujul
    // 5xx - serveripoolsed vead (Springi koodis on midagi valesti) - array index on liiga suur

    @GetMapping("kustuta-toit/{id}")
    public List<Toit> kustutaToit(@PathVariable Long id) {
        toitRepository.deleteById(id);
        return toitRepository.findAll();
    }

    @GetMapping("vaata-toit/{id}") // localhost:8080/vaata-toit/1
    public Toit vaataToit(@PathVariable Long id) {
        return toitRepository.findById(id).orElseThrow();
       // identne: return toitRepository.findById(id).get();
    }

    @GetMapping("toit-valgud/{id}") // localhost:8080/toit-valgud/1
    public int toiduValgud(@PathVariable Long id) {
        int summa = 0;
        List<Toiduaine> toitToiduained = toitRepository.findById(id).orElseThrow().getToiduained();
        for (Toiduaine t: toitToiduained) {
            summa = summa + t.getValk();
        }
        return summa;
    }

    @GetMapping("filtreeri-toit-toiduaine") // localhost:8080/filtreeri-toit-toiduaine?toiduaineId=kartul
    public List<Toit> filtreeriToitToiduaine(@RequestParam String toiduaineId) {
        List<Toit> leitudToidud = new ArrayList<>();
        List<Toit> toidud = toitRepository.findAll();
        for (Toit t : toidud ) {
            for (Toiduaine toiduaine: t.getToiduained()) {
                if (toiduaine.getNimi().contains(toiduaineId)) {
                    leitudToidud.add(t);
                    break;
                }
            }
        }
        return  leitudToidud;
    }

    @Autowired
    ToiduaineUtil toiduaineUtil;

    @GetMapping("toit-min-valgud") // localhost:8080/toit-min-valgud?minValk=20
    public List<Toit> toitMinValgud(@RequestParam int minValk) {
        List<Toit> sobivadToidud = new ArrayList<>();
        List<Toit> toidud = toitRepository.findAll();

        for (Toit t: toidud) {
            List<Toiduaine> toitToiduained = t.getToiduained();
            double summa = toiduaineUtil.saaToiduaineteValgud(toitToiduained);
            if (summa >= minValk) {
                sobivadToidud.add(t);
            }
        }

        return sobivadToidud; // ResponseEntity
    }
}
