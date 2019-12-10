package cst3130.armandokun.webscraping;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScraperManager {

    // Default Constructor
    public ScraperManager() {
    }

    public List<Thread> scrapersList = new ArrayList<>();

    public void startScrapers() {

        // Create the scraper classes
        ArgosScraper scraper1 = new ArgosScraper();
        FreemansScraper scraper3 = new FreemansScraper();
        CurrysScraper scraper4 = new CurrysScraper();

        // Read input from user until they type 'stop'
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!userInput.equals("stop")) {
            userInput = scanner.nextLine();
        }
        scanner.close();

        // Stop threads
        scraper1.stopThread();
        scraper3.stopThread();
        scraper4.stopThread();

        // Wait for threads to finish - join can throw an InterruptedException
        try {
            scraper1.join();
            scraper3.join();
            scraper4.join();
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

    // Start the threads running.
    public void scrapeAll() {
        for (Thread scraper : scrapersList) {
            scraper.start();
        }
    }
}