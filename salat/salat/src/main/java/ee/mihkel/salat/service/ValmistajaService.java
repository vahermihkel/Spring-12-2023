package ee.mihkel.salat.service;

import ee.mihkel.salat.entity.Country;
import ee.mihkel.salat.entity.KontaktAndmed;
import ee.mihkel.salat.entity.Valmistaja;
import org.springframework.stereotype.Service;

@Service
public class ValmistajaService {

    public Valmistaja setValmistajaOmadused(String eesnimi, String perenimi, int vanus, String riik, Country kodakodandsus) {
        Valmistaja valmistaja = new Valmistaja(); // {id: 0, eesnimi: null, perenimi: null, vanus: 0, riik: null}
        valmistaja.setEesnimi(eesnimi);
        valmistaja.setPerenimi(perenimi);
        valmistaja.setVanus(vanus);
        valmistaja.setRiik(riik);
        valmistaja.setKodakondsus(kodakodandsus);
        return valmistaja;
    }

    public KontaktAndmed setValmistajaKontaktandmed(String tel, String aad, String email) {
        KontaktAndmed kontaktAndmed = new KontaktAndmed(); // {id: 0, telefon: null, aadress: null, email: null}
        kontaktAndmed.setTelefon(tel); // {id: 0, telefon: "555", aadress: null, email: null}
        kontaktAndmed.setAadress(aad); // {id: 0, telefon: "555", aadress: "Tammsaare", email: null}
        kontaktAndmed.setEmail(email); // {id: 0, telefon: "555", aadress: "Tammsaare", email: "m@m.com"}
        return kontaktAndmed;
    }

}
