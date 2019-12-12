package cst3130.armandokun.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/** Represents a Product.
    Java annotation is used for the mapping. */
@Entity
@Table(name="products")
public class Products implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "description")
    private String description;

    // Foreign key mapping
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    Set<Phones> phone;

    
    /** Empty constructor */
    public Products(){
    }

        
    /** Returns a String representation of the Product */
    public String toString(){
        String str = "Product id: " + id + "; image url: " + imageUrl + 
        "; description: " + description;
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
