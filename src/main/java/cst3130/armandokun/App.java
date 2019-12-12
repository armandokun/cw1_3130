package cst3130.armandokun;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import cst3130.armandokun.webscraping.*;

public class App {
    public static void main(String[] args) {

        runApplicationAnnotationsConfig();

    }

    static void runApplicationAnnotationsConfig() {
        // Instruct Spring to create and wire beans using annotations.
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get ScraperManager bean
        ScraperManager scrapermng = (ScraperManager) context.getBean("mng");

        // Start scraping with all threads
        scrapermng.scrapeAll();

    }

}
