package cst3130.armandokun.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * Represents a Url.
 * Java annotation is used for the mapping.
 */
@Entity
@Table(name = "url")
public class Urls implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_url")
    private String productUrl;

    // Foreign key mapping to Phones
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "url_id")
    private
    Set<Phones> phone;


    /**
     * Empty constructor
     */
    public Urls() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Set<Phones> getPhone() {
        return phone;
    }

    public void setPhone(Set<Phones> phone) {
        this.phone = phone;
    }
}
