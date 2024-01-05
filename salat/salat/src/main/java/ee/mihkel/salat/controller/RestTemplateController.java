package ee.mihkel.salat.controller;

import ee.mihkel.salat.model.Album;
import ee.mihkel.salat.model.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestTemplateController {

    @GetMapping("saa-albumid") // localhost:8080/saa-albumid?userId=2
    public List<Album> saaAlbumid(@RequestParam int userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://jsonplaceholder.typicode.com/albums";
        ResponseEntity<Album[]> response =  restTemplate.exchange(url, HttpMethod.GET, null, Album[].class);
        Album[] albums = response.getBody();
        List<Album> albumList = Arrays.asList(albums);

//        List<Album> filteredAlbums = new ArrayList<>();
//        for (Album e: albumList) {
//            if (e.getUserId() == userId) {
//                filteredAlbums.add(e);
//            }
//        }
//        return filteredAlbums;
        return albumList.stream() // siin keerab streamiks, ehk teeb tsükli
                .filter(e -> e.getUserId() == userId) // filtreerin ---> võtan ühe ja nimetan teda nt "e" ning küsin tingimust kas jääb alles
                .collect(Collectors.toList()); // tuleb stream tagasi listiks keerata
    }


    @GetMapping("saa-tooted") // localhost:8080/saa-tooted?minPrice=5.5
    public List<Product> saaTooted(@RequestParam double minPrice) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<Product[]> response =  restTemplate.exchange(url, HttpMethod.GET, null, Product[].class);
        Product[] products = response.getBody();
        List<Product> productList = Arrays.asList(products);
        return productList.stream()
                .filter(e -> e.price > minPrice)
                .collect(Collectors.toList());
    }
}
