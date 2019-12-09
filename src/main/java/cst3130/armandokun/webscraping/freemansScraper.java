package cst3130.armandokun.webscraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Freemans Scraper class that is scrapping data.
 */
public class freemansScraper extends Thread {
    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 1;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public freemansScraper() {

    }

    @Override
    public void run() {
        runThread = true;

        while(runThread) {
            try{
                scrapeFreemans();
                sleep(1000 * crawlDelay);//Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            }
            catch(InterruptedException | IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    // Other classes can use this method to terminate the thread.
    public void stopThread() {
        runThread = false;
    }

    /**
     * Scrapes Smartphone data from the Freemans website
     * 
     * @throws IOException
     */
    void scrapeFreemans() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect("https://www.freemans.com/electricals/phones/mobile-phones/_/N-1cZ1rZ1z13u09Z1z141dc?Ntt=phones&refined=leftnav&searchType=FullText").get();

        // Work through pages
        for (int pageNumber = 0; pageNumber <= 96; pageNumber += 48) {

            Document doc1 = Jsoup.connect("https://www.freemans.com/electricals/phones/mobile-phones/_/N-1cZ1rZ1z13u09Z1z141dc?Ntt=phones&refined=leftnav&searchType=FullText&No=" + pageNumber).get();

            // Get all of the products on the page 1
            Elements products = doc.select("li.pContainer");

            if (pageNumber > 0) {
                // Get all of the products in the next page
                products = doc1.select("li.pContainer");
            }

            System.out.println("Page Offset: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select(".pDescriptionContainer");

                // Get the product price
                Elements price1 = products.get(i).select(".pPriceContainer");

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace("£", "");
                
                // Get the image url
                Elements image = products.get(i).select(".pImageContainer");
                String imageUrl = image.select("img").attr("data-original");

                // Get product url
                Elements productLink = products.get(i).select("a");
                String productUrl = productLink.attr("href");

                // Output the data that we have downloaded
                System.out.println("\n FREEMANS: " + description.text() + ";\n PRICE: " + scrapedPrice
                + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);
            }
        }
    }
}
