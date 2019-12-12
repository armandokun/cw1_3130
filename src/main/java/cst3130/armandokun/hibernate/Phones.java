package cst3130.armandokun.hibernate;

import javax.persistence.*;


/** Represents a Phone.
    Java annotation is used for the mapping. */
@Entity
@Table(name="phones")
public class Phones {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "url_id")
    private int urlId;
    
    @Column(name = "product_id")
    private int productId;
    
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

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
