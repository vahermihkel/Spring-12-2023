package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.entity.Toit;
import ee.mihkel.salat.repository.ToiduaineRepository;
import ee.mihkel.salat.repository.ToitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ToitController {

    @Autowired
    ToitRepository toitRepository;
    @Autowired
    ToiduaineRepository toiduaineRepository;

    @GetMapping("koik-toidud") // localhost:8080/koik-toidud
    public List<Toit> koikToidud() {
       return toitRepository.findAll();
    }

    @GetMapping("lisa-toit") // localhost:8080/lisa-toit?id=1&nimetus=Kartulisalat&toiduainedIds=kartul,vorst,hapukoor
    public List<Toit> lisaToit(
            @RequestParam Long id,
            @RequestParam String nimetus,
            @RequestParam List<String> toiduainedIds
//            @RequestParam String[] toiduainedIds
            ) {

        System.out.println(id);
        System.out.println(nimetus);

        Toit toit = new Toit(); // {id: 0, nimetus: "", toiduained: []}
        toit.setId(id);        //  {id: 1, nimetus: "", toiduained: []}
        toit.setNimetus(nimetus); //{id: 1, nimetus: "kartulisalat", toiduained: []}

        List<Toiduaine> toitToiduained = new ArrayList<>(); // SIIA HAKKAN PANEMA TOIDUAINEID MILLE ID KAASA ANTI
        for (String i: toiduainedIds) {
            toitToiduained.add(toiduaineRepository.findById(i).get());
        }
        toit.setToiduained(toitToiduained);

        toitRepository.save(toit);
        return toitRepository.findAll();
    }

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

    @GetMapping("toit-min-valgud") // localhost:8080/toit-min-valgud?minValk=20
    public List<Toit> toitMinValgud(@RequestParam int minValk) {
        List<Toit> sobivadToidud = new ArrayList<>();
        List<Toit> toidud = toitRepository.findAll();

        for (Toit t: toidud) {
            int summa = 0;
            List<Toiduaine> toitToiduained = t.getToiduained();
            for (Toiduaine ta: toitToiduained) {
                summa = summa + ta.getValk();
            }
            if (summa >= minValk) {
                sobivadToidud.add(t);
            }
        }

        return sobivadToidud;
    }
}
