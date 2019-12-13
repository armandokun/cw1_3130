package cst3130.armandokun.hibernate.dao;

import cst3130.armandokun.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class ItemDao {

    //Create an instance of session factory
    private SessionFactory sessionFactory;

    // Empty Constructor
    public ItemDao() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Boolean duplicateExist(String productUrl, Session session) {

        //Find matching products in the database
        List<Urls> urlList = session.createQuery("FROM Urls WHERE productUrl='" + productUrl + "'")
                .getResultList();

        // If there is one or more products, the duplicate exists and there is no need to create a new product
        return urlList.size() >= 1;
    }

}
