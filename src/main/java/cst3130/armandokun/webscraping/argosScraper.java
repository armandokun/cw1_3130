package cst3130.armandokun.webscraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Argos Scraper class that is scrapping data.
 */
public class argosScraper extends Thread {
    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 1;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public argosScraper() {

    }

    @Override
    public void run() {
        runThread = true;

        while(runThread) {
            try{
                scrapeArgos();
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
    void scrapeArgos() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect("https://www.argos.co.uk/search/iphones/category:42793786/").get();

        // Get total number of available pages
        Elements totalPages = doc.select(".hvbaxn");
        String tp = totalPages.text();
        Integer pages = Character.getNumericValue(tp.charAt(tp.length() - 1));

        // Work through pages
        for (int pageNumber = 0; pageNumber <= pages; ++pageNumber) {

            Document doc1 = Jsoup.connect("https://www.argos.co.uk/search/iphones/category:42793786/opt/page:" + pageNumber).get();

            // Get all of the products on the page
            Elements productsLayer = doc.select(".ProductCardstyles__Wrapper-l8f8q8-0");
            Elements products = productsLayer.select(".dlOkBP");

            if (pageNumber > 0) {
                productsLayer = doc1.select(".ProductCardstyles__Wrapper-l8f8q8-0");
                products = productsLayer.select(".dlOkBP");
            }

            System.out.println("Page Number: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select(".ProductCardstyles__Title-l8f8q8-11.kLyOND");

                // Get the product price
                Elements price1 = products.get(i).select(".fHBARv");

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace("£", "");
                Float price = Float.parseFloat(scrapedPrice);

                // Get the image url
                Elements image = products.get(i).select(".dnXqZD");
                String imageUrl = image.select("img").attr("src");

                // Get product url
                Elements productLink = products.get(i).select(".btn-cta");
                String productUrl = productLink.attr("href");

                // Output the data that we have downloaded
                System.out.println("\n ARGOS: " + "\n DESCRIPTION: " + description.text() + ";\n PRICE: " + price
                        + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);
            }
        }
    }
}
