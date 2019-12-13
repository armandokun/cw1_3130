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

    // Foreign key mapping
    @ManyToOne
    @JoinColumn(name = "product_id")
    private
    Products productId;

    // Foreign key mapping
    @ManyToOne
    @JoinColumn(name = "url_id")
    private
    Urls urlId;

    @Column(name = "price")
    private float price;


    /** Empty constructor */
    public Phones(){
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

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public Urls getUrlId() {
        return urlId;
    }

    public void setUrlId(Urls urlId) {
        this.urlId = urlId;
    }
}
