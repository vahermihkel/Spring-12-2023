package ee.mihkel.salat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;
    public Rating rating;
}

@Getter
@Setter
class Rating{
    public double rate;
    public int count;
}

