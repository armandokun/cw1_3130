package cst3130.armandokun.webscraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Freemans Scraper class that is scrapping data.
 */
public class FreemansScraper extends Thread {
    // JSoup CSS selectors

    // Specifies the interval between HTTP requests to the server in seconds.
    public int crawlDelay;

    String storeName;
    String jsoupDoc;
    String jsoupDocOtherPages;
    String productSelector;
    String descriptionSelector;
    String priceSelector;
    String symbolReplacement;
    String imageSelector;
    String imageElSelector;
    String imageUrlSelector;
    String productLinkSelector;
    String productLinkSelectorAttr;

    // Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    // Default Constructor
    public FreemansScraper() {

    }

    @Override
    public void run() {
        runThread = true;

        while (runThread) {
            try {
                scrapeFreemans();
                sleep(1000 * crawlDelay);// Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
            } catch (InterruptedException | IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    // Terminates the thread.
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
        Document doc = Jsoup.connect(jsoupDoc).get();

        // Work through pages
        for (int pageNumber = 0; pageNumber <= 96; pageNumber += 48) {

            Document doc1 = Jsoup.connect(jsoupDocOtherPages + pageNumber).get();

            // Get all of the products on the page 1
            Elements products = doc.select(productSelector);

            if (pageNumber > 0) {
                // Get all of the products in the next page
                products = doc1.select(productSelector);
            }

            System.out.println("Page Offset: " + pageNumber);

            // Work through the products
            for (int i = 0; i < products.size(); ++i) {

                // Get the product description
                Elements description = products.get(i).select(descriptionSelector);

                // Get the product price
                Elements price1 = products.get(i).select(priceSelector);

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace(symbolReplacement, "");

                // Get the image url
                Elements image = products.get(i).select(imageSelector);
                String imageUrl = image.select(imageElSelector).attr(imageUrlSelector);

                // Get product url
                Elements productLink = products.get(i).select(productLinkSelector);
                String productUrl = productLink.attr(productLinkSelectorAttr);

                // Output the data that we have downloaded
                System.out.println("\n " + storeName + ": " + description.text() + ";\n PRICE: " + scrapedPrice
                        + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);
            }
        }
    }

    public int getCrawlDelay() {
        return crawlDelay;
    }

    public void setCrawlDelay(int crawlDelay) {
        this.crawlDelay = crawlDelay;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getJsoupDoc() {
        return jsoupDoc;
    }

    public void setJsoupDoc(String jsoupDoc) {
        this.jsoupDoc = jsoupDoc;
    }

    public String getJsoupDocOtherPages() {
        return jsoupDocOtherPages;
    }

    public void setJsoupDocOtherPages(String jsoupDocOtherPages) {
        this.jsoupDocOtherPages = jsoupDocOtherPages;
    }

    public String getProductSelector() {
        return productSelector;
    }

    public void setProductSelector(String productSelector) {
        this.productSelector = productSelector;
    }

    public String getDescriptionSelector() {
        return descriptionSelector;
    }

    public void setDescriptionSelector(String descriptionSelector) {
        this.descriptionSelector = descriptionSelector;
    }

    public String getPriceSelector() {
        return priceSelector;
    }

    public void setPriceSelector(String priceSelector) {
        this.priceSelector = priceSelector;
    }

    public String getSymbolReplacement() {
        return symbolReplacement;
    }

    public void setSymbolReplacement(String symbolReplacement) {
        this.symbolReplacement = symbolReplacement;
    }

    public String getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(String imageSelector) {
        this.imageSelector = imageSelector;
    }

    public String getImageElSelector() {
        return imageElSelector;
    }

    public void setImageElSelector(String imageElSelector) {
        this.imageElSelector = imageElSelector;
    }

    public String getImageUrlSelector() {
        return imageUrlSelector;
    }

    public void setImageUrlSelector(String imageUrlSelector) {
        this.imageUrlSelector = imageUrlSelector;
    }

    public String getProductLinkSelector() {
        return productLinkSelector;
    }

    public void setProductLinkSelector(String productLinkSelector) {
        this.productLinkSelector = productLinkSelector;
    }

    public String getProductLinkSelectorAttr() {
        return productLinkSelectorAttr;
    }

    public void setProductLinkSelectorAttr(String productLinkSelectorAttr) {
        this.productLinkSelectorAttr = productLinkSelectorAttr;
    }

    public boolean isRunThread() {
        return runThread;
    }

    public void setRunThread(boolean runThread) {
        this.runThread = runThread;
    }
}
