package cst3130.armandokun.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateAnnotation {
    //Creates new Sessions when we need to interact with the database
    private SessionFactory sessionFactory;


    /**
     * Empty constructor
     */
    public HibernateAnnotation() {
    }


    /**
     * Sets up the session factory.
     * Call this method first.
     */
    public void init() {

    }

    /** Adds a new product to the database */
    public void addCereal(){
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        //Create an instance of a product class
        Products product = new Products();

        //Set values of product class that we want to add
        product.setImageUrl("that's an imageUrl");
        product.setDescription("THIS IS A DESC");

        //Start transaction
        session.beginTransaction();

        //Add product to database - will not be stored until we commit the transaction
        session.save(product);

        //Commit transaction to save it to database
        session.getTransaction().commit();

        //Close the session and release database connection
        session.close();
        System.out.println("product added to database with ID: " + product.getId());
    }
}
