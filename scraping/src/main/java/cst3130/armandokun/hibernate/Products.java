package cst3130.armandokun.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * Represents a Product.
 * Java annotation is used for the mapping.
 */
@Entity
@Table(name = "products")
public class Products implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "store_name")
    private String storeName;

    // Foreign key mapping
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private
    Set<Phones> phone;


    /**
     * Empty constructor
     */
    public Products() {
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Set<Phones> getPhone() {
        return phone;
    }

    public void setPhone(Set<Phones> phone) {
        this.phone = phone;
    }

}
