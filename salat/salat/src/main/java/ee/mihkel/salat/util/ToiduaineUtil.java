package ee.mihkel.salat.util;

import ee.mihkel.salat.entity.Toiduaine;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToiduaineUtil {

    public double saaToiduaineteValgud(List<Toiduaine> toiduained) {
        double summa = 0;
        for (Toiduaine t : toiduained) {
            summa = summa + t.getValk();
        }
        return summa;
    }
}



// Service - läheb ühte kindlasse kontrollerisse funktsioone tegema
// Util - läheb mitmetesse kontrolleritesse, erinevatesse kohtadesse fnktse tegema
