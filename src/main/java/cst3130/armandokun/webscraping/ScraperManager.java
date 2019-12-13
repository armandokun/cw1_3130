package cst3130.armandokun.webscraping;

import java.util.ArrayList;
import java.util.List;

public class ScraperManager extends Thread{

    // Default Constructor
    public ScraperManager() {
    }

    public List<Thread> scrapersList = new ArrayList<>();

    // Start the threads running.
    public void scrapeAll() {
        for (Thread scraper : scrapersList) {
            scraper.start();
        }

        // Wait for threads to finish - join can throw an InterruptedException
        try {
            for (Thread scraper : scrapersList) {
                scraper.join();
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted exception thrown: " + ex.getMessage());
        }

        System.out.println("Web scraping complete");

    }

    public List<Thread> getScrapersList() {
        return scrapersList;
    }

    public void setScrapersList(List<Thread> scrapersList) {
        this.scrapersList = scrapersList;
    }
}
