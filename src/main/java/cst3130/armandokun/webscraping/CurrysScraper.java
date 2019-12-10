package cst3130.armandokun.webscraping;

import java.io.IOException;
// import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Freemans Scraper class that is scrapping data.
 */
public class CurrysScraper extends Thread {
    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 2;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public CurrysScraper() {

    }

    @Override
    public void run() {
        runThread = true;

        while (runThread) {
            try {
                scrapeCurrys();
                sleep(1000 * crawlDelay);// Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            } catch (InterruptedException | IOException ex) {
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
    void scrapeCurrys() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect(
                "https://www.currys.co.uk/gbuk/phones-broadband-and-sat-nav/mobile-phones-and-accessories/mobile-phones/362_3412_32041_xx_xx/xx-criteria.html")
                .get();

        // Get total number of available pages

        // Selects ul class named pagination
        Elements totalPages = doc.select(".pagination");

        // Discovers size of list elements inside of ul
        Elements totalLiElements = totalPages.select("li");
        int pages = totalLiElements.size();

        // Work through pages
        for (int pageNumber = 1; pageNumber < pages; ++pageNumber) {

            // Converts int to String for correct url doc1
            String jsoupGet = String.valueOf(
                    "https://www.currys.co.uk/gbuk/phones-broadband-and-sat-nav/mobile-phones-and-accessories/mobile-phones/362_3412_32041_xx_xx/"
                            + pageNumber + "_20/relevance-desc/xx-criteria.html");

            // Download HTML document from website for the next page
            Document doc1 = Jsoup.connect(jsoupGet).get();

            // Get all of the products on the page 1
            Elements products = doc.select("article.product.result-prd.productCompare.clearfix");

            if (pageNumber > 1) {
                // Get all of the products in the next page
                products = doc1.select("article.product.result-prd.productCompare.clearfix");
            }

            System.out.println("\n Page Number: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select(".productTitle");

                // Get the product price
                Elements price1 = products.get(i).select(".price");

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace("£", "");

                // Get the image url
                Elements image = products.get(i).select(".lozadImage");
                String imageUrl = image.select("source").attr("srcset");

                // Get product url
                Elements productLink = products.get(i).select("a");
                String productUrl = productLink.attr("href");

                // Output the data that we have downloaded
                System.out.println("\n CURRYS: " + description.text() + ";\n PRICE: " + scrapedPrice + ";\n IMAGE_URL: "
                        + imageUrl + ";\n PRODUCT_URL: " + productUrl + ";");
            }
        }
    }
}
