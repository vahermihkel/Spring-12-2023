package ee.mihkel.salat.service;

import ee.mihkel.salat.entity.Toiduaine;
import ee.mihkel.salat.repository.ToiduaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToiduaineService {

//    private final ToiduaineRepository toiduaineRepository;
//
//    public ToiduaineService(ToiduaineRepository toiduaineRepository) {
//        this.toiduaineRepository = toiduaineRepository;
//    }

    @Autowired
    ToiduaineRepository toiduaineRepository;

    public List<Toiduaine> saaToiduained(List<String> toiduainedIds) {
        List<Toiduaine> toitToiduained = new ArrayList<>(); // SIIA HAKKAN PANEMA TOIDUAINEID MILLE ID KAASA ANTI
        for (String i: toiduainedIds) {
            toitToiduained.add(toiduaineRepository.findById(i).get());
        }
        return toitToiduained;
    }
}
