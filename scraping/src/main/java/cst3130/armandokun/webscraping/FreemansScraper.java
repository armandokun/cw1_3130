package cst3130.armandokun.webscraping;

import cst3130.armandokun.hibernate.*;

import java.io.IOException;

import cst3130.armandokun.hibernate.dao.ItemDao;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Freemans Scraper class that is scrapping data.
 */
public class FreemansScraper extends Thread {
    // JSoup CSS selectors

    // Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay;

    private String storeName;
    private String jsoupDoc;
    private String jsoupDocOtherPages;
    private String productSelector;
    private String descriptionSelector;
    private String priceSelector;
    private String symbolReplacement;
    private String imageSelector;
    private String imageElSelector;
    private String imageUrlSelector;
    private String productLinkSelector;
    private String productLinkSelectorAttr;

    // Create objects to store info from website
    private Products product = new Products();
    private Phones phone = new Phones();
    private Urls url = new Urls();

    // Class that generates sessionFactory
    private ItemDao productDao = new ItemDao();

    // Default Constructor
    public FreemansScraper() {

    }

    @Override
    public void run() {
        try {
            scrapeFreemans();
            sleep(1000 * crawlDelay);// Sleep is in milliseconds, so we need to multiply the crawl delay by 1000
        } catch (InterruptedException | IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Scrapes Smartphone data from the Freemans website
     *
     * @throws IOException
     */
    private void scrapeFreemans() throws IOException {
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
            for (Element productItem : products) {

                // Initiating session
                Session session = productDao.getSessionFactory().getCurrentSession();

                // Get the product description
                Elements description = productItem.select(descriptionSelector);
                product.setDescription(description.text());

                // Get the product price
                Elements price1 = productItem.select(priceSelector);

                // Deletes pound symbol from the price and formats to float
                String scrapedPrice = price1.text().replace(symbolReplacement, "");
                String cleanPrice = scrapedPrice.replace(",", "");
                float price = Float.parseFloat(cleanPrice);
                phone.setPrice(price);

                // Get the image url
                Elements image = productItem.select(imageSelector);
                String imageUrl = image.select(imageElSelector).attr(imageUrlSelector);
                product.setImageUrl(imageUrl);

                // Get product url
                Elements productLink = productItem.select(productLinkSelector);
                String productUrl = productLink.attr(productLinkSelectorAttr);
                url.setProductUrl(productUrl);

                product.setStoreName(storeName);

                // Output the data that we have downloaded
                System.out.println("\n " + storeName + ": " + description.text() + ";\n PRICE: " + scrapedPrice
                        + ";\n IMAGE_URL: " + imageUrl + ";\n PRODUCT_URL: " + productUrl);

                // Begin transaction
                session.beginTransaction();

                // Check if there are duplicates
                if (!productDao.duplicateExist(productUrl, session)) {

                    // Set Foreign keys
                    phone.setProductId(product);
                    phone.setUrlId(url);

                    // Saving to the Session, ready for saving to DB
                    session.save(url);
                    session.save(product);
                    session.save(phone);

                    // Commit transaction to save it to DB
                    session.getTransaction().commit();

                    // Close the session and release database connection
                    session.close();
                } else {

                    // Update
                    session.update(phone);
                    session.update(product);
                    session.close();
                }
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

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Phones getPhone() {
        return phone;
    }

    public void setPhone(Phones phone) {
        this.phone = phone;
    }

    public Urls getUrl() {
        return url;
    }

    public void setUrl(Urls url) {
        this.url = url;
    }

    public ItemDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ItemDao productDao) {
        this.productDao = productDao;
    }
}
