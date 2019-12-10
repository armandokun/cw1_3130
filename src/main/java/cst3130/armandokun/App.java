package cst3130.armandokun;

import cst3130.armandokun.webscraping.ScraperManager;

public class App {
    public static void main(String[] args) {

        // Creates an object instance of ScraperManager
        ScraperManager sm = new ScraperManager();

        // Starts the scraping threads from ScraperManager
        sm.startScrapers();

    }

}
