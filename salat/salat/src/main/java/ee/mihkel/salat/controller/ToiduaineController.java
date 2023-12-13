package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.repository.ToiduaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ToiduaineController {

    @Autowired
    ToiduaineRepository toiduaineRepository;

//    public ToiduaineController(ToiduaineRepository toiduaineRepository) {
//    }

//    @GetMapping("for")   // localhost:8080/for
//    public void teeForTsykkel() {
//        for (int i = 0; i < 10; i++) {
//            System.out.println("Käivitasin");
//        }
//    }
//
//    @GetMapping("string")   // localhost:8080/string
//    public String tagastaString() {
//        return "Hello world";
//    }

    @GetMapping("toiduaine")   // localhost:8080/toiduaine
    public Toiduaine saaToiduaine() {
        return new Toiduaine();
    }

    @GetMapping("lisa-toiduaine/{nimi}/{rasv}/{valk}/{sysivesik}")   // localhost:8080/lisa-toiduaine/kartul/1/2/40
    public List<Toiduaine> lisaToiduaine(@PathVariable String nimi, @PathVariable int rasv,
                                   @PathVariable int valk, @PathVariable int sysivesik) {
//        List<String> stringid = new ArrayList<>();
        Toiduaine toiduaine = new Toiduaine(nimi, rasv, sysivesik, valk);
        // {nimi: "kartul", rasv: 1, sysivesik: 2, valk: 40}
        toiduaineRepository.save(toiduaine);
        return toiduaineRepository.findAll(); // "Successfully saved: kartul"        {nimi: "kartul", rasv: 1, sysivesik: 2, valk: 40}
        //   [{nimi: "kartul", rasv: 1, sysivesik: 2, valk: 40}, {nimi: "vorst", rasv: 15, sysivesik: 18, valk: 10}]
    }


    @GetMapping("lisa-toiduaine2")   // localhost:8080/lisa-toiduaine2?nimi=kartul&rasv=1&valk=2&sysivesik=40
    public List<Toiduaine> lisaToiduaine2(@RequestParam String nimi, @RequestParam int rasv,
                                          @RequestParam int valk, @RequestParam int sysivesik) {
        Toiduaine toiduaine = new Toiduaine(nimi, rasv, sysivesik, valk);
        toiduaineRepository.save(toiduaine);
        return toiduaineRepository.findAll();
    }

    // liida kokku kõikide Toiduainete valgud mis on andmebaasis
    @GetMapping("koik-valgud")   // localhost:8080/koik-valgud
    public int koikValgudKokku() {
        int summa = 0;
//        summa = summa + 5; //   5  = 0 + 5
//        summa = summa + 2; //   7  = 5 + 2
        //                      toiduaineRepository.count()
        List<Toiduaine> toiduained = toiduaineRepository.findAll();
//        for (int i = 0; i < toiduained.size(); i++) {
//            summa = summa + toiduained.get(i).getValk();
//        }
        for (Toiduaine toiduaine : toiduained) {
            summa += toiduaine.getValk();
        }
        return summa;
    }
}
