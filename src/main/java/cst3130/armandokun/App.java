package cst3130.armandokun;

import java.util.Scanner;
import cst3130.armandokun.webscraping.*;

public class App {
    public static void main(String[] args) {

        //Create the scraper classes
        argosScraper scraper1 = new argosScraper();
        onbuyScraper scraper2 = new onbuyScraper();
        freemansScraper scraper3 = new freemansScraper();

        //Start the threads running.
        scraper1.start();
        scraper2.start();
        scraper3.start();

        //Read input from user until they type 'stop'
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        while(!userInput.equals("stop")){
            userInput = scanner.nextLine();
        }
        
        //Stop threads
        scraper1.stopThread();
        scraper2.stopThread();
        scraper3.stopThread();
        
        //Wait for threads to finish - join can throw an InterruptedException
        try{
            scraper1.join();
            scraper2.join();
            scraper3.join();
        }
        catch(InterruptedException ex){
            System.out.println("Interrupted exception thrown: " + ex.getMessage());
        }
        
        System.out.println("Web scraping complete");
    }
}
