package cst3130.armandokun.hibernate.dao;

import cst3130.armandokun.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class ItemDao {

    //Create an instance of session factory
    private SessionFactory sessionFactory;

    /**
     * Empty constructor
     */
    public ItemDao() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Boolean duplicateExist(String domain, String path, String queryString, Session session) {
        //Find matching products in the database
        List<Urls> urlList = session.createQuery("FROM url WHERE domain='" + domain +
                "' AND path='" + path + "' AND query_string='" + queryString + "'")
                .getResultList();

        //If there is one or more products, the duplicate exists and there is no need to create a new product
        if (urlList.size() >= 1) {
            return true;
            //Else we return false - no duplicates
        } else {
            return false;
        }
    }
}
