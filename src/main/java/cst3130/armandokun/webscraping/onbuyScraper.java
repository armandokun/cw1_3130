package cst3130.armandokun.webscraping;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * OnBuy Scraper class that is for scrapping data from Onbuy website.
 */
public class OnbuyScraper extends Thread {

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

    // Default Constructor
    public OnbuyScraper() {
    }

    @Override
    public void run() {

        try {
            scrapeOnbuy();
            sleep(1000 * crawlDelay);// Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
        } catch (InterruptedException | IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Scrapes Samsung phones data from the Onbuy website
     * 
     * @throws IOException
     */
    void scrapeOnbuy() throws IOException {
        // Download HTML document from website
        Document doc = Jsoup.connect(jsoupDoc).get();

        // Work through pages
        for (int pageNumber = 0; pageNumber <= 120; pageNumber += 30) {

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
                Float price = Float.parseFloat(scrapedPrice);

                // Get the image url
                Elements image = products.get(i).select(imageSelector);
                String imageUrl = image.select(imageElSelector).attr(imageUrlSelector);

                // Get product url
                Elements productLink = products.get(i).select(productLinkSelector);
                String productUrl = productLink.attr(productLinkSelectorAttr);

                // Output the data that we have downloaded
                System.out.println("\n " + storeName + ": " + description.text() + ";\n PRICE: " + price
                        + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);
            }
        }
    }

    // Getters & Setters

    public int getCrawlDelay() {
        return crawlDelay;
    }

    public void setCrawlDelay(int crawlDelay) {
        this.crawlDelay = crawlDelay;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getImageElSelector() {
        return imageElSelector;
    }

    public void setImageElSelector(String imageElSelector) {
        this.imageElSelector = imageElSelector;
    }
}
