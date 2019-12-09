package cst3130.armandokun.webscraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Argos Scraper class that is scrapping data.
 */
public class onbuyScraper extends Thread {
    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 1;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public onbuyScraper() {

    }

    @Override
    public void run() {
        runThread = true;

        while(runThread) {
            try{
                scrapeAmazon();
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
     * Scrapes cornflakes data from the Ocado website
     * 
     * @throws IOException
     */
    void scrapeAmazon() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect("https://www.onbuy.com/gb/search/?query=samsung&category=12871").get();

        // Work through pages
        for (int pageNumber = 0; pageNumber <= 120; pageNumber += 30) {

            Document doc1 = Jsoup.connect("https://www.onbuy.com/gb/search/?query=samsung&category=12871&offset=" + pageNumber).get();

            // Get all of the products on the page 1
            Elements products = doc.select("div.product.options.sponsored");

            if (pageNumber > 0) {
                // Get all of the products in the next page
                products = doc1.select("div.product.options.sponsored");
            }

            System.out.println("Page Offset: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select(".name");

                // Get the product price
                Elements price1 = products.get(i).select(".value");

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace("From £", "");
                Float price = Float.parseFloat(scrapedPrice);

                // Get the image url
                Elements image = products.get(i).select(".image");
                String imageUrl = image.select("img").attr("src");

                // Get product url
                Elements productLink = products.get(i).select("a");
                String productUrl = productLink.attr("href");

                // Output the data that we have downloaded
                System.out.println("\n ONBUY: " + description.text() + ";\n PRICE: " + price
                + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);
            }
        }
    }
}
