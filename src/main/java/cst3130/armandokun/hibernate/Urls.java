package cst3130.armandokun.hibernate;

import javax.persistence.*;


/** Represents a Url.
    Java annotation is used for the mapping. */
@Entity
@Table(name="url")
public class Urls {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "domain")
    private int domain;
    
    @Column(name = "path")
    private int path;
    
    @Column(name = "query_string")
    private float queryString;

    
    /** Empty constructor */
    public Urls(){
    }
    

        
    /** Returns a String representation of the Url */
    public String toString(){
        String str = "Url id: " + id + "; domain: " + domain + 
        "; path: " + path + "; query_string: " + queryString;
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public float getQueryString() {
        return queryString;
    }

    public void setQueryString(float queryString) {
        this.queryString = queryString;
    }

}
