package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.entity.model.ToiduaineDTO;
import ee.mihkel.salat.repository.ToiduaineRepository;
import ee.mihkel.salat.util.ToiduaineUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@Log4j2
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
        return new Toiduaine(); // <--- tagastab kogu aeg tühja Toiduaine: {nimi: null, valk: 0, rasv: 0, sysivesik: 0}
    } // oli läbimängimiseks

    @GetMapping("toiduained")   // localhost:8080/toiduained?page=0&size=3
    public Page<Toiduaine> saaToiduained(Pageable pageable) {
        return toiduaineRepository.findAll(pageable);
    }

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("toiduained-dto")   // localhost:8080/toiduained-dto
    public List<ToiduaineDTO> saaToiduainedDTO() {
//        ModelMapper modelMapper = new ModelMapper();
//        System.out.println("Hakkasin võtma toiduaineid");
        log.info("Hakkasin võtma toiduaineid");
        // log.debug() <-- ei ole LIVE keskkonnas
//        System.out.println(modelMapper);
        log.error(modelMapper); // KUI tekib, siis saadab automaatselt e-maili meie tööemailile
        return toiduaineRepository.findAll().stream()
                .map(t -> modelMapper.map(t, ToiduaineDTO.class))
                .toList();
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
    public double koikValgudKokku() {
        List<Toiduaine> toiduained = toiduaineRepository.findAll();
        return toiduaineUtil.saaToiduaineteValgud(toiduained);
    }

    @GetMapping("koik-rasvad")   // localhost:8080/koik-rasvad
    public int koikRasvadKokku() {
        int summa = 0;
        List<Toiduaine> toiduained = toiduaineRepository.findAll();
        for (Toiduaine toiduaine : toiduained) {
            summa += toiduaine.getRasv();
        }
        return summa;
    }

    @Autowired
    ToiduaineUtil toiduaineUtil;

    @GetMapping("keskmine-valk")   // localhost:8080/keskmine-valk
    public double keskmineValk() {
        List<Toiduaine> toiduained = toiduaineRepository.findAll();
        double summa = toiduaineUtil.saaToiduaineteValgud(toiduained);
        return summa/toiduaineRepository.findAll().size();
    }

    @GetMapping("valk-vahemikus")   // localhost:8080/valk-vahemikus?minValk=1&maxValk=10
    public List<Toiduaine> valkVahemikus(
            @RequestParam int minValk,
            @RequestParam int maxValk
    ) {
//        List<Toiduaine> sobivadToiduained = new ArrayList<>();
//        List<Toiduaine> toiduained = toiduaineRepository.findAll();
//        for (Toiduaine t : toiduained) {
//            if (t.getValk() > minValk && t.getValk() < maxValk) {
//                sobivadToiduained.add(t);
//            }
//        }
        return toiduaineRepository.findAllByValkBetween(minValk, maxValk);
    }

    @GetMapping("kustuta-toiduaine/{nimi}")
    public List<Toiduaine> kustutaToiduaine(@PathVariable String nimi) {

        toiduaineRepository.deleteById(nimi);
        return toiduaineRepository.findAll();
    }
}
