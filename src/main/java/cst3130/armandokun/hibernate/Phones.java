package cst3130.armandokun.hibernate;

import javax.persistence.*;
import java.io.Serializable;


/** Represents a Phone.
    Java annotation is used for the mapping. */
@Entity
@Table(name="phones")
public class Phones implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Foreign key mapping to product
    @ManyToOne
    @JoinColumn(name = "product_id")
    Products productId;

    //Foreign key mapping to url
    @ManyToOne
    @JoinColumn(name = "url_id")
    Urls urlId;

    @Column(name = "price")
    private float price;


    /** Empty constructor */
    public Phones(){
    }
    

        
    /** Returns a String representation of the Cereal */
    public String toString(){
        String str = "Phone id: " + id + "; productTypeId: " + productId + 
        "; urlId: " + urlId + "; price: " + price;
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
