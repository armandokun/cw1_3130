package cst3130.armandokun.webscraping;

import java.io.IOException;
// import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * LaptopsDirect Scraper class that is scrapping data.
 */
public class LaptopsDirect extends Thread {
    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public LaptopsDirect() {

    }

    @Override
    public void run() {
        runThread = true;

        while(runThread) {
            try{
                scrapeLaptopsDirect();
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
     * Scrapes Smartphone data from the Currys website
     * 
     * @throws IOException
     * 
     */
    void scrapeLaptopsDirect() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect("https://www.laptopsdirect.co.uk/ct/phones-and-pdas/smartphones").get();

        // Get total number of available pages

        // Selects ul class named pagination
        Elements totalPages = doc.select(".sr_numresults");

        // Discovers size of list elements inside of ul
        Elements totalLiElements = totalPages.select("b:nth-child(2)");
        int pages = Integer.parseInt(totalLiElements.first().text());

        // Work through pages
            // HTML shows only 24 results per page
        for (int pageNumber = 1; pageNumber < pages/24+1; ++pageNumber) {

            //Converts int to String for correct url doc1
            String jsoupGet = String.valueOf("https://www.laptopsdirect.co.uk/ct/phones-and-pdas/smartphones?pageNumber=" + pageNumber);

            // Download HTML document from website for the next page
            Document doc1 = Jsoup.connect(jsoupGet).get();

            // Get all of the products on the page 1
            Elements products = doc.select(".OfferBox");

            if (pageNumber > 1) {
                // Get all of the products in the next page
                products = doc1.select(".OfferBox");
            }

            System.out.println("\n Page Number: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select("a.offerboxtitle");

                // Get the product price
                Elements price1 = products.get(i).select("span.offerprice");

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace("£", "");
                
                // Get the image url
                Elements image = products.get(i).select(".offerImage");
                String imageUrl = image.attr("src");

                // Get product url
                Elements productLink = products.get(i).select("a.offerboxlink.btn-prime");
                String productUrl = productLink.attr("href");

                // Output the data that we have downloaded
                System.out.println("\n LAPTOPSDIRECT: " + description.text() + ";\n PRICE: " + scrapedPrice
                + ";\n IMAGE_URL: https://www.laptopsdirect.co.uk" + imageUrl + ";\n PRODUCT_URL: https://www.laptopsdirect.co.uk" + productUrl + ";");
            }
        }
    }
}
