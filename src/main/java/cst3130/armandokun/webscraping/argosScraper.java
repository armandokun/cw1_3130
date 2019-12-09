package cst3130.armandokun.webscraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/** Example code to illustrate web scraping with JSoup */
public class argosScraper {
    
    
    /** Constructor */
    public argosScraper(){
        try{
            scrapeArgos();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    /** Scrapes cornflakes data from the Ocado website */
    void scrapeArgos() throws Exception{
        //Name of item that we want to scrape
        String itemName = "iphones";
        
        //Download HTML document from website
        Document doc = Jsoup.connect("https://www.argos.co.uk/search/" + itemName).get();
        
        //Get all of the products on the page
        Elements productsLayer = doc.select(".ProductCardstyles__Wrapper-l8f8q8-0");
        Elements products = productsLayer.select(".dlOkBP");
        
        //Work through the products
        for(int i=0; i<products.size(); ++i){
            
            //Get the product description
            Elements description = products.get(i).select(".ProductCardstyles__Title-l8f8q8-11.kLyOND");
            
            //Get the product price
            Elements price1 = products.get(i).select(".fHBARv");

            //Deletes pound symbol from the price and formats to float
            String scrapedPrice = price1.text().replace("£", ""); 
            Float price = Float.parseFloat(scrapedPrice);

            //Get the image url
            Elements image = products.get(i).select(".dnXqZD");
            String imageUrl = image.select("img").attr("src");

            //Get product url
            Elements productLink = products.get(i).select(".btn-cta");
            String productUrl = productLink.attr("href");
            
            //Output the data that we have downloaded
            System.out.println("\n ARGOS: " + i + "\n DESCRIPTION: " + description.text() + ";\n PRICE: " + price + ";\n IMAGE_URL: " + imageUrl
            + ";\n PRODUCT_URL: " + productUrl + ";");
        }
    }
}
