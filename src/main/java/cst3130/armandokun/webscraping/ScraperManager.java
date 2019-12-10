package cst3130.armandokun.webscraping;

import java.util.Scanner;

public class ScraperManager {

    // Default Constructor
    public ScraperManager() {

    }

    public void startScrapers() {

        // Create the scraper classes
        ArgosScraper scraper1 = new ArgosScraper();
        OnbuyScraper scraper2 = new OnbuyScraper();
        FreemansScraper scraper3 = new FreemansScraper();
        CurrysScraper scraper4 = new CurrysScraper();
        LaptopsDirect scraper5 = new LaptopsDirect();

        // Start the threads running.
        scraper1.start();
        scraper2.start();
        scraper3.start();
        scraper4.start();
        scraper5.start();

        // Read input from user until they type 'stop'
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while (!userInput.equals("stop")) {
            userInput = scanner.nextLine();
        }
        scanner.close();

        // Stop threads
        scraper1.stopThread();
        scraper2.stopThread();
        scraper3.stopThread();
        scraper4.stopThread();
        scraper5.stopThread();

        // Wait for threads to finish - join can throw an InterruptedException
        try {
            scraper1.join();
            scraper2.join();
            scraper3.join();
            scraper4.join();
            scraper5.join();
        } catch (InterruptedException ex) {
            System.out.println("Interrupted exception thrown: " + ex.getMessage());
        }

        System.out.println("Web scraping complete");

    }
}