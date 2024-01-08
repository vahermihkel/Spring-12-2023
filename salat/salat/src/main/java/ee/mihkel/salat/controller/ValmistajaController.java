package ee.mihkel.salat.controller;

import ee.mihkel.salat.entity.*;
import ee.mihkel.salat.entity.model.ValmistajaDTO;
import ee.mihkel.salat.repository.ToitRepository;
import ee.mihkel.salat.repository.ValmistajaRepository;
import ee.mihkel.salat.service.ValmistajaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ValmistajaController {

    private final ValmistajaRepository valmistajaRepository;
    private final ToitRepository toitRepository;

    private final ValmistajaService valmistajaService;

    public ValmistajaController(ValmistajaRepository valmistajaRepository,
                                ToitRepository toitRepository,
                                ValmistajaService valmistajaService) {
        this.valmistajaRepository = valmistajaRepository;
        this.toitRepository = toitRepository;
        this.valmistajaService = valmistajaService;
    }

//    @Autowired
//    ValmistajaService valmistajaService;

    // localhost:8080/lisa-valmistaja?eesnimi=Peeter&perenimi=Laane&vanus=30&riik=ESTONIA?tel=555&aad=Tammsaare&email=m@m.com
    //http://localhost:8080/lisa-valmistaja?eesnimi=Peeter&perenimi=Laane&vanus=30&&riik=Läti&mitmesRiik=1&tel=555&aad=Tammsaare&email=m@m.com
    @GetMapping("lisa-valmistaja")
    public List<Valmistaja> lisaValmistaja(
            @RequestParam String eesnimi, @RequestParam String perenimi,
            @RequestParam int vanus, @RequestParam String riik ,@RequestParam int mitmesRiik,
            @RequestParam String tel, @RequestParam String aad, @RequestParam String email) {
        Valmistaja valmistaja = valmistajaService.setValmistajaOmadused(
                eesnimi, perenimi, vanus, riik, Country.values()[mitmesRiik]
        );

        KontaktAndmed kontaktAndmed = valmistajaService.setValmistajaKontaktandmed(tel, aad, email);
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
    public List<ValmistajaDTO> saaValmistajad() {
        List<Valmistaja> valmistajad = valmistajaRepository.findAll();
        List<ValmistajaDTO> valmistajadDTOd = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Valmistaja v: valmistajad) {
            ValmistajaDTO valmistajaDTO = modelMapper.map(v, ValmistajaDTO.class);
            valmistajadDTOd.add(valmistajaDTO);

//            ValmistajaDTO valmistajaDTO = new ValmistajaDTO();
//            valmistajaDTO.setEesnimi(v.getEesnimi());
//            valmistajaDTO.setRiik(v.getRiik());
//            valmistajadDTOd.add(valmistajaDTO);
        }
        return valmistajadDTOd;
    }

    @GetMapping("valmistajad-dto")   // localhost:8080/valmistajad-dto
    public List<ValmistajaDTO> saaValmistajadDTO() {
        ModelMapper modelMapper = new ModelMapper();
        List<ValmistajaDTO> valmistajadDTOd = valmistajaRepository.findAll().stream()
                .map(v -> modelMapper.map(v, ValmistajaDTO.class))
                .toList();
        return valmistajadDTOd;
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
