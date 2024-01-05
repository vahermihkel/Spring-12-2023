package ee.mihkel.salat.service;

import ee.mihkel.salat.entity.Kodutoo1;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Kodutoo1Service {

    //                                      [{id: 1, number: 3},{id: 2, number: 5},2,6,2]
    public double arvutaKeskmine(List<Kodutoo1> numbrid) {
        double summa = 0;
        for (Kodutoo1 k : numbrid) {
            summa = summa + k.getNumber();
            // 3  =  0    + 3
            // 8  =  3    + 5
            //10  =  8    + 2
        }
        return summa / numbrid.size();
    }
}
