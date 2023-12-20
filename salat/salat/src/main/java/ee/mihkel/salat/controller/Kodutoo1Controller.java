package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.Kodutoo1;
import ee.mihkel.salat.repository.Kodutoo1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Kodutoo1Controller {

    @Autowired
    Kodutoo1Repository kodutoo1Repository;

    @GetMapping("numbrid")
    public List<Kodutoo1> saaNumbrid() {
        return kodutoo1Repository.findAll();
    }

    @GetMapping("lisa-number") // localhost:8080/lisa-number?id=1&number=8
    public List<Kodutoo1> lisaNumber(
            @RequestParam Long id,
            @RequestParam int number
    ) {
        Kodutoo1 kodutoo1 = new Kodutoo1();
        // {id: 0, number: 0} <- kui ei tee set()
        kodutoo1.setNumber(number);
        // {id: 0, number: 8} <- kui paneme URLs 8
        kodutoo1.setId(id);
        // {id: 1, number: 8} <- kui paneme URLs 1
        kodutoo1Repository.save(kodutoo1);
        return kodutoo1Repository.findAll();
    }

    @GetMapping("kustuta-number") // localhost:8080/kustuta-number?id=5
    public List<Kodutoo1> kustutaNumber(
            @RequestParam Long id
    ) {
        kodutoo1Repository.deleteById(id);
        return kodutoo1Repository.findAll();
    }

    @GetMapping("numbrite-koguarv") // localhost:8080/numbrite-koguarv
    public int numbriteKoguarv() {
        return kodutoo1Repository.findAll().size();
    }

    @GetMapping("numbrite-arv") // localhost:8080/numbrite-arv?arv=5
    public int numbriteArv(@RequestParam int arv) {
        List<Kodutoo1> kodutoo1s = new ArrayList<>();
        for ( Kodutoo1 k : kodutoo1Repository.findAll()) {
            if (k.getNumber() > arv) {
                kodutoo1s.add(k);
            }
        }
        return kodutoo1s.size();
    }

    // [{"id":1,"number":15},{"id":11,"number":8},{"id":21,"number":8},{"id":2,"number":8}]
    // k={"id":1,"number":15}
    // k={"id":11,"number":8}

//    @GetMapping("lisa-film") // localhost:8080/lisa-film?filmiUnikaalsuseTunnus=1&actoriteUnikaalsedTunnused=5,8,10
//    public List<Film> lisaFilm(
//            @RequestParam Long filmiUnikaalsuseTunnus,
//            @RequestParam String nimi,
//            @RequestParam int aasta,
//            @RequestParam String riik,
//            @RequestParam List<Long> actoriteUnikaalsedTunnused  // 5,8,10
//    ) {
//        Film uusFilm = new Film(); // andmebaasitabeli tüübi mille väärtused on kõik tühjad
//        uusFilm.setId(filmiUnikaalsuseTunnus);
//        uusFilm.setNimi(nimi);
//        uusFilm.setAasta(aasta);
//        uusFilm.setRiik(riik);
//              // ArrayList <- sinna on võimalik juurde liita, kustutada jne
//        List<Actor> filmileLisatavadActorid = new ArrayList<>(); // [] <- Siia sisse saab panna ainult tüüpi "Actor"
//        for (Long i : actoriteUnikaalsedTunnused) {
//            Actor lisatavActor = actorRepository.findById(i).get();
//            filmileLisatavadActorid.add(lisatavActor);
//        }
////        for (Long 5 : [5,8,10,1,0]) {
////            Actor lisatavActor = actorRepository.findById(5).get(); ---> Võtab Actori, kes on Andmebaasis IDga 5
////            filmileLisatavadActorid.add(lisatavActor); ---> Lisab selle Actori minu tühja Listi
////        }
////        for (Long 8 : [5,8,10,1,0]) {
////            Actor lisatavActor = actorRepository.findById(8).get(); ---> Võtab Actori, kes on Andmebaasis IDga 8
////            filmileLisatavadActorid.add(lisatavActor); ---> Lisab selle Actori minu tühja Listi
////        }
//        for (int i = 0; i < actoriteUnikaalsedTunnused.size(); i++) {
//            Long id = actoriteUnikaalsedTunnused.get(i); // 5, 8
//        }
//        // Long 5 :  [5,8,10, 1, 0]
//        // Long 8 :  [5,8,10, 1, 0]
//        // Long 10 :  [5,8,10, 1, 0]
//        // Long 1 :  [5,8,10, 1, 0]
//        // Long 0 :  [5,8,10, 1, 0]
////        for (Long i : actoriteUnikaalsedTunnused) {
////            filmileLisatavadActorid.add(actorRepository.findById(i).get());
////        }
//        uusFilm.setActorid(filmileLisatavadActorid);
//
//        filmidRepository.save(uusFilm);
//        return filmidRepository.findAll();
//    }
}
